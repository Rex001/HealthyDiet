package com.healthydiet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout layout = (LinearLayout) findViewById(R.id.title_ll);
		final ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		params.weight = 1;
		
		TextView textView = new TextView(this);
		textView.setLayoutParams(params);
		textView.setText("tab1");
		textView.setTextColor(Color.BLACK);
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
			}
		});
		layout.addView(textView);
		textView = new TextView(this);
		textView.setLayoutParams(params);
		textView.setText("tab2");
		textView.setTextColor(Color.BLACK);
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
			}
		});
		layout.addView(textView);
		textView = new TextView(this);
		textView.setLayoutParams(params);
		textView.setText("tab3");
		textView.setTextColor(Color.BLACK);
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(2);
			}
		});
		layout.addView(textView);
		textView = new TextView(this);
		textView.setLayoutParams(params);
		textView.setText("tab4");
		textView.setTextColor(Color.BLACK);
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(3);
			}
		});
		layout.addView(textView);
		
		int count = layout.getChildCount();
		
		final View view = findViewById(R.id.view);
		view.setBackgroundColor(Color.RED);
		
		DisplayMetrics dm = new DisplayMetrics();getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		
		final int w = width/count;
		
		LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
		p.width = width/count;
		view.setLayoutParams(p);
		
		
		List<View> viewList = new ArrayList<View>();
		for (int i = 1; i < count + 1; i++) {
			textView = new TextView(this);
			textView.setText("tab"+i);
			viewList.add(textView);
		}
		ViewPagerAdapter adapter = new ViewPagerAdapter(viewList);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				System.out.println("position="+position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				System.out.println("position="+position+", positionOffset="+positionOffset+", positionOffsetPixels="+positionOffsetPixels);
//				view.scrollTo((int) (w * position + w*positionOffset), 0);
				LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
				p.leftMargin = (int) (w * position + w*positionOffset);
				view.setLayoutParams(p);
				System.out.println("scrollTo="+(int) (w * position + w*positionOffset));
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				System.out.println("state="+state);
			}
		});
	}
	
	public class ViewPagerAdapter extends PagerAdapter {

		private List<View> views;
		ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public int getCount() {
			return views.size();
		}

		public Object getItem(int position) {
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position),params);
			return views.get(position);
		}
	}
}
