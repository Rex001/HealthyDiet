package com.healthydiet.common;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.healthydiet.util.LogUtil;

public class BaseApplication extends Application {

	private LogUtil log = new LogUtil(getClass().getSimpleName());

	@Override
	public void onCreate() {
		super.onCreate();
		AppCrashHandler crashHandler = AppCrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		AjaxCallback.setNetworkLimit(24);
		AjaxCallback.setTimeout(15000);
		BitmapAjaxCallback.setCacheLimit(100);

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory() + "/falcongolf/aquerycache/";
			File f = new File(path);
			AQUtility.setCacheDir(f);
		}
//		startLocationClient(new MyLocationListener());
	}
	
	public void startLocationClient(BDLocationListener locationListener) {
		LocationClient mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(locationListener);
		mLocationClient.setLocOption(setLocationOption(LocationMode.Hight_Accuracy, "gcj02", 0, true, true));
		mLocationClient.start();
	}

	private LocationClientOption setLocationOption(LocationMode mLocationMode, String mCoordType, int mScanSpan, boolean mIsNeedDirection, boolean mIsNeedAddress) {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(mLocationMode);
		option.setCoorType(mCoordType);
		option.setScanSpan(mScanSpan);
		option.setNeedDeviceDirect(mIsNeedDirection);
		option.setIsNeedAddress(mIsNeedAddress);
		return option;
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			log.i(sb.toString());
			SharedPreferences spf = getSharedPreferences(CommonData.SPREFERENCES_NAME, Context.MODE_PRIVATE);
			Editor editor = spf.edit();
			editor.putString("location", "" + location.getLatitude() + "," + location.getLongitude());
			editor.commit();
		}

		@Override
		public void onReceivePoi(BDLocation location) {

		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		BitmapAjaxCallback.clearCache();
	}
}
