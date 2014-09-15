package com.healthydiet.util;

import java.util.Map;

import org.json.JSONObject;

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
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				log.v(key + " --> " + params.get(key));
			}
		} else {
			log.v("params --> null");
		}
		aq.ajax(url, params, JSONObject.class, new com.androidquery.callback.AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jsonObject, AjaxStatus status) {
				log.v("status.getCode()=" + status.getCode() + ", jsonObject=" + (jsonObject == null ? "null" : jsonObject.toString()));
				try {
					callback.callback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (status.getCode() == 200 && jsonObject != null) {
					try {
						boolean result = jsonObject.optBoolean("success");
						if (result) {
							callback.onComplete(jsonObject);
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
