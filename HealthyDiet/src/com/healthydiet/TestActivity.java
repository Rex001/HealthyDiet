package com.healthydiet;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

public class TestActivity extends PullActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(context);
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(listView);
	}
	
	
}
