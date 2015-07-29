package com.hhinns.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;

public class FavorableInfoMapActivity extends BaseActivity implements OnClickListener 
,BusinessResponse,OnMapLoadedCallback{
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private Intent mIntent = null;
	
	/**
	 *   baidu  map
	 */
	private MapView mMapView;
	private BaiduMap mMap;
	
	private TextView tvDate;
	private TextView tvNumber;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			break;
		case R.id.tvMore:
			HotelListActivity.dataModel.searchHotelMore(OrderActivity.search);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_favorable_info_map);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mIntent = getIntent();
		
		

		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.map);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.GONE);
		tvDate = (TextView)findViewById(R.id.tvDate);
		tvDate.setText(CommonUtils.getStringByCalender(Calendar.getInstance()));
		tvNumber = (TextView)findViewById(R.id.tvNumber);
		tvNumber.setText(FavorableInfoActivity.dataModel.hotelList.get(0).get("count")+"间特价房");

		if (null != mIntent) {
			String title = mIntent.getStringExtra("title");
			if (!TextUtils.isEmpty(title)) {
				mTvTitle.setText(title);
			}
			
		}
		
		mMapView = (MapView)findViewById(R.id.bmapView);
		mMap = mMapView.getMap();
		mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); 
		mMap.setOnMapLoadedCallback(this);
		setMarker();
	}



	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onResume();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}
	
	private void setMarker()
	{
		if(FavorableInfoActivity.dataModel.hotelList.size()>0)
		{
			for(int i=0;i<FavorableInfoActivity.dataModel.hotelList.size();i++)
			{
				LatLng point = new LatLng(
						Double.valueOf(FavorableInfoActivity.dataModel.hotelList.get(i).get("lat")),
						Double.valueOf(FavorableInfoActivity.dataModel.hotelList.get(i).get("lon"))); 
				BitmapDescriptor bitmap = BitmapDescriptorFactory  
			    	.fromBitmap(CommonUtils.getMapMarkerIcon(FavorableInfoActivity.dataModel.hotelList.get(i).get("hotelName"),
			    			"￥"+FavorableInfoActivity.dataModel.hotelList.get(i).get("price"), 
			    			FavorableInfoMapActivity.this,
			    			FavorableInfoMapActivity.this));
			    OverlayOptions option = new MarkerOptions()  
			    	.position(point)  
			    	.icon(bitmap)
			    	.title(i+"")
			    	.visible(true);  
			  mMap.addOverlay(option);
			}
		}
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// TODO Auto-generated method stub
				    mMap.hideInfoWindow();
				    marker.setVisible(false);
	                InfoWindow mInfoWindow;  
	                View view = LayoutInflater.from(FavorableInfoMapActivity.this).inflate(R.layout.infowindow_map, null);
	                TextView title =(TextView)view.findViewById(R.id.tvTileInfo);  
	                TextView price =(TextView)view.findViewById(R.id.tvPriceInfo);  
	                title.setText(FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("hotelName"));
	                price.setText(FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("price"));
	                final LatLng ll = marker.getPosition();  
	                Point p = mMap.getProjection().toScreenLocation(ll);  
	                LatLng llInfo = mMap.getProjection().fromScreenLocation(p);  
	                mInfoWindow = new InfoWindow(view, llInfo, 0);
	                view.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							mMap.hideInfoWindow();
							marker.setVisible(true);
							Intent intent = new Intent(getApplicationContext(),
									RoomListActivity.class);
							intent.putExtra("hotelName", FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("hotelName"));
							intent.putExtra("id", FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("id"));
							intent.putExtra("lat", FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("lat"));
							intent.putExtra("lon", FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("lon"));
							intent.putExtra("addr", FavorableInfoActivity.dataModel.hotelList.get(Integer.parseInt(marker.getTitle())).get("addr"));
							startActivity(intent);
						}
					});
	                //显示InfoWindow  
	                mMap.showInfoWindow(mInfoWindow); 
				return true;
			}
		});
		
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		if(FavorableInfoActivity.dataModel.hotelList.size()>0)
		{
			 MapStatus mMapStatus = new MapStatus.Builder()  
		        .target(new LatLng(Double.valueOf(FavorableInfoActivity.dataModel.hotelList.get(0).get("lat")),
						Double.valueOf(FavorableInfoActivity.dataModel.hotelList.get(0).get("lon"))))  
		        .zoom(14)  
		        .build();  
			 MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);  
		        //改变地图状态  
		     mMap.setMapStatus(mMapStatusUpdate); 
		}
		
	}
}
