package com.healthydiet.util;

import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.healthydiet.common.CommonData;

public class AQueryProxy {
	private LogUtil log = new LogUtil(getClass().getSimpleName());

	private AQuery aq;

	public AQueryProxy(AQuery aq) {
		this.aq = aq;
	}

	public void ajax(String url, Map<String, ?> params, final AjaxCallback callback) {
		log.v("url --> " + url);
		for (String key : params.keySet()) {
			log.v(key + " --> " + params.get(key));
		}
		aq.ajax(url, params, String.class, new com.androidquery.callback.AjaxCallback<String>() {
			@Override
			public void callback(String url, String callbackStr, AjaxStatus status) {
				log.v("status.getCode()=" + status.getCode() + ", callbackStr=" + callbackStr);
				try {
					callback.callback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (status.getCode() == 200 && !TextUtils.isEmpty(callbackStr)) {
					try {
						JSONTokener jsonParser = new JSONTokener(callbackStr);
						JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
						JSONObject headObject = jsonObject.getJSONObject("head");
						int code = headObject.getInt("code");
						if (code == CommonData.ResultCode.SUCCESS.value) {
							callback.onComplete(jsonObject);
						} else if (code == CommonData.ResultCode.FAIL.value) {
							Toast.makeText(aq.getContext(), headObject.getString("msg"), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(aq.getContext(), CommonData.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(aq.getContext(), CommonData.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(aq.getContext(), CommonData.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public interface AjaxCallback {
		public void callback() throws Exception;
		public void onComplete(JSONObject jsonObject) throws Exception;
	}
}
