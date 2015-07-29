package com.hhinns.main.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
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
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.main.HotelListActivity;
import com.hhinns.main.HotelMapListActivity;
import com.hhinns.main.MainActivity;
import com.hhinns.main.NearbyHotelActivity;
import com.hhinns.main.OrderActivity;
import com.hhinns.main.R;
import com.hhinns.main.RoomListActivity;
import com.hhinns.main.ui.RouteLineFragment.MyLocationListenner;
import com.hhinns.model.ProccessForNearBy;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearbyFacilityFragment extends Fragment implements
		OnClickListener, BusinessResponse, OnMapLoadedCallback {
	private View mView = null;
	/**
	 * baidu map
	 */
	private MapView mMapView;
	private BaiduMap mMap;
	private ProccessForNearBy dataModel;
	private RoutePlanSearch mPoiSearch;
	private PlanNode stNode;
	private PlanNode enNode;
	private LinearLayout layout_line;
	private ImageView mIvCar = null;
	private ImageView mIvBus = null;
	private ImageView mIvPerson = null;
	private LocationClient mLocClient;
	public MyLocationListenner myListener = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		mView = inflater.inflate(R.layout.fragment_room_map_list, container,
				false);
		initComponents();
		return mView;
	}

	private void initComponents() {
		mMapView = (MapView) mView.findViewById(R.id.bmapView);
		mMap = mMapView.getMap();
		mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMap.setOnMapLoadedCallback(this);
		mMap.setMyLocationEnabled(true);
		mPoiSearch = RoutePlanSearch.newInstance();
		mPoiSearch.setOnGetRoutePlanResultListener(listener);
		layout_line = (LinearLayout)mView.findViewById(R.id.layout_line);
		mIvCar = (ImageView) mView.findViewById(R.id.ivCar);
		mIvCar.setOnClickListener(this);
		mIvBus = (ImageView) mView.findViewById(R.id.ivBus);
		mIvBus.setOnClickListener(this);
		mIvPerson = (ImageView) mView.findViewById(R.id.ivPerson);
		mIvPerson.setOnClickListener(this);
		

		// dataModel = new ProccessForNearBy(getActivity());
		// dataModel.nearNew(MainActivity.mLocation.longitude,
		// MainActivity.mLocation.latitude);
		// dataModel.addResponseListener(this);

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if (null != responseStatus && responseStatus.equals("ok")) {
			// if(dataModel.nearList.size()>0)
			// {
			// MapStatus mMapStatus = new MapStatus.Builder()
			// // .target(new
			// LatLng(Double.valueOf(dataModel.nearList.get(0).get("lat")),
			// // Double.valueOf(dataModel.nearList.get(0).get("lon"))))
			// .target(new LatLng(Double.valueOf("34.2317910000"),
			// Double.valueOf("108.9465950000")))
			// .zoom(14)
			// .build();
			// MapStatusUpdate mMapStatusUpdate =
			// MapStatusUpdateFactory.newMapStatus(mMapStatus);
			// //改变地图状态
			// setMarker();
			// mMap.setMapStatus(mMapStatusUpdate);
			// }
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	private void setMarker() {
		if (RoomListActivity.isNear) {
			if (null!=RoomListActivity.dataModel_.nearList&&RoomListActivity.dataModel_.nearList.size() > 0) {
				for (int i = 0; i < RoomListActivity.dataModel_.nearList.size(); i++) {
					if(null!=RoomListActivity.dataModel_.nearList
							.get(i).get("lat")&&!"".equals(RoomListActivity.dataModel_.nearList
									.get(i).get("lat")))
					{
					LatLng point = new LatLng(
							Double.valueOf(RoomListActivity.dataModel_.nearList
									.get(i).get("lat")),
							Double.valueOf(RoomListActivity.dataModel_.nearList
									.get(i).get("lon")));
					BitmapDescriptor bitmap = BitmapDescriptorFactory
							.fromBitmap(CommonUtils
									.getMapMarkerIcon(
											RoomListActivity.dataModel_.nearList
													.get(i).get("name")+"-"+
													RoomListActivity.dataModel_.nearList
													.get(i).get("dis")+"米-"+RoomListActivity.dataModel_.nearList
													.get(i).get("phone"),
											 RoomListActivity.dataModel_.nearList
															.get(i)
															.get("address")+"-"+
															RoomListActivity.dataModel_.nearList
															.get(i)
															.get("desc"),
											getActivity(),
											getActivity()));
					OverlayOptions option = new MarkerOptions().position(point)
							.icon(bitmap).title(i + "").visible(true);
					mMap.addOverlay(option);
					}
				}
			}
		}else
		{
			if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon)
			{
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromBitmap(CommonUtils
							.getMapMarkerIcon(
									RoomListActivity.title,
									 RoomListActivity.address,
									getActivity(),
									getActivity()));
			OverlayOptions option = new MarkerOptions().position(new LatLng(Double.valueOf(RoomListActivity.lat), Double.valueOf(RoomListActivity.lon)))
					.icon(bitmap).title("").visible(true);
			mMap.addOverlay(option);
			}
		}

	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon) {
			MapStatus mMapStatus = new MapStatus.Builder()
					 .target(new
					 LatLng(Double.valueOf(RoomListActivity.lat),
					 Double.valueOf(RoomListActivity.lon)))
//					.target(new LatLng(Double.valueOf("34.2317910000"), Double
//							.valueOf("108.9465950000")))
							.zoom(14).build();
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
			setMarker();
			mMap.setMapStatus(mMapStatusUpdate);
		} else {
			MapStatus mMapStatus = new MapStatus.Builder()
					.target(new LatLng(Double
							.valueOf(MainActivity.mLocation.latitude), Double
							.valueOf(MainActivity.mLocation.longitude)))
					.zoom(14).build();
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
			setMarker();
			mMap.setMapStatus(mMapStatusUpdate);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ivCar:
			mPoiSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode)  
				    .to(enNode));
			mIvCar.setSelected(true);
			mIvBus.setSelected(false);
			mIvPerson.setSelected(false);
			break;
		case R.id.ivBus:
			mPoiSearch.transitSearch((new TransitRoutePlanOption())  
				    .from(stNode)  
				    .city(MainActivity.mLocation.city)  
				    .to(enNode));
			mIvCar.setSelected(false);
			mIvBus.setSelected(true);
			mIvPerson.setSelected(false);
			break;
		case R.id.ivPerson:
			mPoiSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
			mIvCar.setSelected(false);
			mIvBus.setSelected(false);
			mIvPerson.setSelected(true);
			break;

		default:
			break;
		}
		
		
	}

	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
		public void onGetWalkingRouteResult(WalkingRouteResult result) {
			try {
				mMap.clear();
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					CommonUtils.showToast(getActivity(), "步行路线查询失败");
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					WalkingRouteOverlay overlay = new WalkingRouteOverlay(mMap);
					mMap.setOnMarkerClickListener(overlay);
					overlay.setData(result.getRouteLines().get(0));
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public void onGetTransitRouteResult(TransitRouteResult result) {
			try {
				mMap.clear();
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					CommonUtils.showToast(getActivity(), "公交路线查询失败");
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {

					TransitRouteOverlay overlay = new TransitRouteOverlay(mMap);
					mMap.setOnMarkerClickListener(overlay);
					overlay.setData(result.getRouteLines().get(0));
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			try {
				mMap.clear();
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					CommonUtils.showToast(getActivity(), "驾车路线查询失败");
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					DrivingRouteOverlay overlay = new DrivingRouteOverlay(mMap);
					mMap.setOnMarkerClickListener(overlay);
					overlay.setData(result.getRouteLines().get(0));
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};
	
	public void Viewchange()
	{
		if(RoomListActivity.isNear)
		{
			mMap.clear();
			layout_line.setVisibility(View.GONE);
			if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon)
			{
			MapStatus mMapStatus = new MapStatus.Builder()
			 .target(new
			 LatLng(Double.valueOf(RoomListActivity.lat),
			 Double.valueOf(RoomListActivity.lon)))
//			.target(new LatLng(Double.valueOf("34.2317910000"), Double
//					.valueOf("108.9465950000")))
					.zoom(14).build();
	        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
			.newMapStatus(mMapStatus);
	        setMarker();
	         mMap.setMapStatus(mMapStatusUpdate);
			}
	// 改变地图状态
		}else
		{
			 mMap.clear();
			initPosiation();
			layout_line.setVisibility(View.VISIBLE);
			if(null!=MainActivity.mLocation.latitude&&null!=MainActivity.mLocation.longitude)
			{
			stNode = PlanNode.withLocation(new LatLng(Double.valueOf(MainActivity.mLocation.latitude),Double.valueOf(MainActivity.mLocation.longitude)));  
			}
			if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon&&!RoomListActivity.lat.equals("")&&!RoomListActivity.lon.equals(""))
			{
			enNode = PlanNode.withLocation(new LatLng(Double.valueOf(RoomListActivity.lat), Double.valueOf(RoomListActivity.lon)));
			}
//			stNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2317910000"), Double.valueOf("108.9465950000")));  
//			enNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2407720000"), Double.valueOf("108.9275450000")));
			
			if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon)
			{
				MapStatus mMapStatus = new MapStatus.Builder()
						.target(new LatLng(Double.valueOf(RoomListActivity.lat), Double.valueOf(RoomListActivity.lon)))
					.zoom(14)
//						.target(new LatLng(Double.valueOf("34.2317910000"), Double.valueOf("108.9465950000")))
						.build();
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				setMarker();
				mMap.setMapStatus(mMapStatusUpdate);
			}
		}
		
	}
	/**
	 * 定位
	 */
	private void initPosiation() {
		myListener = new MyLocationListenner();
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 监听函数，修改新位置的时候.http://www.baidu.com/link?url=zAKtzY_Joi6yXB0sxVHYdYAsjs-IVyQr3xBJ8WSjqPGeVCea_RLYoUFiRO9I874WesqVbKxOpWLTncQZ0agEcK
	 */
	public class MyLocationListenner implements BDLocationListener {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			String address = null;
			if(address ==null)
			{
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
				if (location.hasAddr()) {
					address = location.getAddrStr();
					MainActivity.mLocation.setLatitude(String.valueOf(location.getLatitude()));
//					MainActivity.mLocation.setLatitude("34.2317910000");
					MainActivity.mLocation.setLongitude(String.valueOf(location.getLongitude()));
//					MainActivity.mLocation.setLongitude("108.9465950000");
					MainActivity.mLocation.setCity(String.valueOf(location.getCity()));
					MainActivity.mLocation.setCityid(String.valueOf(location.getCityCode()));
				}
			}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

}
