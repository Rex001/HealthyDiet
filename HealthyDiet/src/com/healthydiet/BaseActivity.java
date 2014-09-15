package com.healthydiet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.healthydiet.util.ActivityManager;
import com.healthydiet.util.LogUtil;

public class BaseActivity extends Activity {
	
	protected ProgressDialog progressDialog;
	public Handler handler = new Handler();
	protected BaseActivity context;
	private boolean isDestroy = true;
	
	
	public LogUtil log = new LogUtil(getClass().getSimpleName());
	protected AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ActivityManager.getInstance().addActivity(this);
		context = this;
		isDestroy = false;
		aq = new AQuery(context);
	}

	@Override
	protected void onDestroy() {
		dismissProgressDialog();
		isDestroy = true;
		handler = null;
		super.onDestroy();
		ActivityManager.getInstance().getActivityList().remove(this);
		if (isTaskRoot()) {
			long triggerSize = 20000000; // 大于20M时候开始清除
			long targetSize = 5000000; // 直到少于5M
			AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
		}
	}

	public void showProgressDialog() {
		if (isDestroy) {
			return;
		}
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.get_info));
			progressDialog.setCanceledOnTouchOutside(false);
		} else {
			progressDialog.show();
		}
	}

	public void dismissProgressDialog() {
		if (isDestroy) {
			return;
		}
		if (handler != null) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (null != progressDialog) {
						try {
							progressDialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

	}

	public void toast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public void shake(View view) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		view.startAnimation(shake);
	}

	public void shake(int id) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		aq.id(id).animate(shake);
	}
	
	public void hideSoftInput(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);// 隐藏软键盘
	}
	
	public void showSoftInput(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);// 显示软键盘
	}

}
