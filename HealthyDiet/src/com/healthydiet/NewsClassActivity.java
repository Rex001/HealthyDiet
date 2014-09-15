package com.healthydiet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.healthydiet.common.CommonData;
import com.healthydiet.model.NewsClass;
import com.healthydiet.util.AQueryProxy;
import com.healthydiet.util.AQueryProxy.AjaxCallback;

public class NewsClassActivity extends PullActivity implements OnClickListener {
	
	private List<NewsClass> list = new ArrayList<NewsClass>();
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		aq.id(R.id.titlelayout_title_tv).text("健康咨询");
		aq.id(R.id.titlelayout_left_btn).clicked(this);
		adapter = new MyAdapter();
		aq.id(R.id.newsclass_lv).adapter(adapter);
		getData();
	}

	private void getData() {
		refreshLayout.setRefreshing(true);
		new Thread() {
			@Override
			public void run() {
				new AQueryProxy(aq).ajax(CommonData.SEVER_URL + "news/newsclass", null, new AjaxCallback() {

					@Override
					public void onComplete(JSONObject jsonObject) throws Exception {
						List<NewsClass> list = new ArrayList<NewsClass>();
						JSONArray array = jsonObject.optJSONArray("yi18");
						if (array != null) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject json = array.getJSONObject(i);
								NewsClass newsclass = new NewsClass();
								newsclass.id = json.optInt("id");
								newsclass.name = json.optString("name");
								list.add(newsclass);
							}
						}
						NewsClassActivity.this.list = list;
						adapter.notifyDataSetChanged();
					}

					@Override
					public void callback() throws Exception {
						refreshLayout.setRefreshing(false);
					}
				});
			};
		}.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlelayout_left_btn:
			finish();
			break;

		default:
			break;
		}
	}
	
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.class_item, null);
			}
			AQuery aq = new AQuery(convertView);
			NewsClass newsClass = list.get(position);
			aq.id(R.id.class_item_name_tv).text(newsClass.name);
			return convertView;
		}
		
	}

}
