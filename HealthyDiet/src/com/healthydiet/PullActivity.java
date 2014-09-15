package com.healthydiet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;

public class PullActivity extends BaseActivity {
	
	protected SwipeRefreshLayout refreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.pull_layout);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_widget);
		refreshLayout.setEnabled(false);
		refreshLayout.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh() {
				refreshLayout.setRefreshing(true);
			}
		});
	}
	
	@Override
	public void setContentView(int layoutResID) {
		View view = LayoutInflater.from(context).inflate(layoutResID, null);
		setContentView(view);
	}
	
	@Override
	public void setContentView(View view) {
		refreshLayout.addView(view);
		refreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
	}
	
}
