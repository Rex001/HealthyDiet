package com.healthydiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends BaseActivity implements OnClickListener {
	
	private SlidingPaneLayout slidingPaneLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.home_slidinglayout);
		aq.id(R.id.titlelayout_left_btn).text("菜单").clicked(this);
		aq.id(R.id.home_news).clicked(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlelayout_left_btn:
			if (slidingPaneLayout.isOpen()) {
				slidingPaneLayout.closePane();
			} else {
				slidingPaneLayout.openPane();
			}
			break;
		case R.id.home_news:
			startActivity(new Intent(context, NewsClassActivity.class));
			break;

		default:
			break;
		}
	}
	
}
