package com.hhinns.main.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
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
import com.hhinns.main.MainActivity;
import com.hhinns.main.R;
import com.hhinns.main.RoomListActivity;
import com.hhinns.main.MainActivity.MyLocationListenner;

public class RouteLineFragment extends Fragment implements OnClickListener,
		BusinessResponse, OnMapLoadedCallback {
	private View mView = null;
	private ImageView mIvCar = null;
	private ImageView mIvBus = null;
	private ImageView mIvPerson = null;
	/**
	 * baidu map
	 */
	private MapView mMapView;
	private BaiduMap mMap;
	private RoutePlanSearch mPoiSearch;
	private PlanNode stNode;
	private PlanNode enNode;
	
	private LocationClient mLocClient;
	public MyLocationListenner myListener = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		mView = inflater.inflate(R.layout.fragment_room_map_list, container,
				false);
		initPosiation();
		initComponents();
		return mView;
	}

	private void initComponents() {

		mMapView = (MapView) mView.findViewById(R.id.bmapView);

		mIvCar = (ImageView) mView.findViewById(R.id.ivCar);
		mIvCar.setOnClickListener(this);
		mIvBus = (ImageView) mView.findViewById(R.id.ivBus);
		mIvBus.setOnClickListener(this);
		mIvPerson = (ImageView) mView.findViewById(R.id.ivPerson);
		mIvPerson.setOnClickListener(this);

		mMap = mMapView.getMap();
		mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMap.setOnMapLoadedCallback(this);
		mMap.setMyLocationEnabled(true);

		mPoiSearch = RoutePlanSearch.newInstance();;
		mPoiSearch.setOnGetRoutePlanResultListener(listener);

		if(null!=MainActivity.mLocation.latitude&&null!=MainActivity.mLocation.longitude)
		{
		stNode = PlanNode.withLocation(new LatLng(Double.valueOf(MainActivity.mLocation.latitude),Double.valueOf(MainActivity.mLocation.longitude)));  
		}
//		else
//		{
			stNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2317910000"), Double.valueOf("108.9465950000")));  
//		}
//		stNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2317910000"), Double.valueOf("108.9465950000")));  
//		enNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2407720000"), Double.valueOf("108.9275450000")));
		if(null!=RoomListActivity.lat&&null!=RoomListActivity.lon&&!RoomListActivity.lat.equals("")&&!RoomListActivity.lon.equals(""))
		{
		enNode = PlanNode.withLocation(new LatLng(Double.valueOf(RoomListActivity.lat), Double.valueOf(RoomListActivity.lon)));
		}
//		else
//		{
			enNode = PlanNode.withLocation(new LatLng(Double.valueOf("34.2407720000"), Double.valueOf("108.9275450000")));
//		}
		
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		if(null!=MainActivity.mLocation.latitude&&null!=MainActivity.mLocation.longitude)
		{
			MapStatus mMapStatus = new MapStatus.Builder()
//					.target(new LatLng(Double.valueOf(MainActivity.mLocation.latitude),
//							Double.valueOf(MainActivity.mLocation.longitude)))
				.zoom(14)
					.target(new LatLng(Double.valueOf("34.2317910000"), Double.valueOf("108.9465950000")))
					.build();
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
		mPoiSearch.destroy();
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

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub

	}

	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {  
	    public void onGetWalkingRouteResult(WalkingRouteResult result) {  
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
	    }  
	    public void onGetTransitRouteResult(TransitRouteResult result) {  
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
	    }  
	    public void onGetDrivingRouteResult(DrivingRouteResult result) {  
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
	    	
	    }  
	};
	
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
					MainActivity.mLocation.setLongitude(String.valueOf(location.getLongitude()));
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
