package com.hhinns.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BaseModel;
import com.hhinns.dataprocess.BeeCallback;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.main.MainActivity;
import com.hhinns.protocol.RESERVATION;
import com.hhinns.protocol.SEARCH;

public class RoomModel extends BaseModel {

	public List<Map<String, String>> roomlList = null;
	public List<Map<String, String>> serviceList = null;
	public List<Map<String, String>> nearList = null;
	public List<String> imageList = null;
	public String specialService;
	public String baseInstallation;
	public String aboutUs;
	public String transportation;
	public  String totle;
	public HashMap<String, String> orderDetail;
	public String pay_url;

	public RoomModel(Context context) {
		super(context);
		roomlList = new ArrayList<Map<String, String>>();
		serviceList = new ArrayList<Map<String, String>>();
		nearList = new ArrayList<Map<String,String>>();
		imageList = new ArrayList<String>();
		orderDetail = new HashMap<String, String>();
	}

	public void rooms(String id) {
		String url = ProtocolConst.ROOM_LIST;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				if(null==jo)
				return;
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						if(null!=jo.optJSONArray("data"))
						{
						JSONArray data = jo.optJSONArray("data");
						if (!roomlList.isEmpty())
							roomlList.clear();
						if (null != data && data.length() > 0) {
							Map<String, String> map = null;
							for (int i = 0; i < data.length(); i++) {
								JSONObject items = data.getJSONObject(i);
								map = new HashMap<String, String>();
								map.put("price", items.getString("room_price"));
								map.put("roomtype",
										items.getString("room_type"));
								map.put("operate", "预定");
								map.put("id", items.getString("hotel_id"));
								map.put("des", items.getString("remark"));
								map.put("roomState", items.getString("Status"));
								map.put("pictrue", items.getString("Picture"));
								map.put("special", items.getString("Special"));
								map.put("count", items.getString("room_count"));
								map.put("roomtypeid",
										items.getString("RoomType_ID"));
								roomlList.add(map);
							}
						}
						}else
						{
							if (!roomlList.isEmpty())
								roomlList.clear();
							Map<String, String> map = null;
							JSONObject items = jo.optJSONObject("data");
							map = new HashMap<String, String>();
							map.put("price", items.getString("room_price"));
							map.put("roomtype",
									items.getString("room_type"));
							map.put("operate", "预定");
							map.put("id", items.getString("hotel_id"));
							map.put("des", items.getString("remark"));
							map.put("roomState", items.getString("Status"));
							map.put("pictrue", items.getString("Picture"));
							map.put("special", items.getString("Special"));
							map.put("count", items.getString("room_count"));
							map.put("roomtypeid",
									items.getString("RoomType_ID"));
							roomlList.add(map);
							
						}
					}
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	public void near(String lon,String lat) {
		String url = ProtocolConst.NEAR_HOTEL;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						if(null!=jo.optJSONArray("data"))
						{
						JSONArray data = jo.optJSONArray("data");
						if (!nearList.isEmpty())
							nearList.clear();
						if (null != data && data.length() > 0) {
							Map<String, String> map = null;
							for (int i = 0; i < data.length(); i++) {
								JSONObject items = data.getJSONObject(i);
								map.put("id", items.getString("hotel_id"));
								map.put("name",
										items.getString("hotel_name"));
								map.put("price",
										items.getString("room_price"));
								map.put("lat",
										items.getString("Lat"));
								map.put("lon",
										items.getString("Lon"));
								nearList.add(map);
							}
						}
						}else
						{
							if (!nearList.isEmpty())
								nearList.clear();
							Map<String, String> map = new HashMap<String, String>();
							JSONObject items = jo.optJSONObject("data");
							map.put("id", items.getString("hotel_id"));
							map.put("name",
									items.getString("hotel_name"));
							map.put("price",
									items.getString("room_price"));
							map.put("lat",
									items.getString("Lat"));
							map.put("lon",
									items.getString("Lon"));
							nearList.add(map);
						}
					}
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("latitude", MainActivity.mLocation.latitude);
		params.put("longitude", MainActivity.mLocation.longitude);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void nearNew(String lon,String lat)
	{
		lon = MainActivity.mLocation.longitude; lat = MainActivity.mLocation.latitude;
		String url=String.format("?c=nearby&a=getnearbys&lat=%s&lon=%s"
				,lat,lon);
		aq.ajax(url,JSONObject.class,this,"callback");
	}
	
	

	public void hotelDetail(String id) {
		String url = ProtocolConst.HOTEL_INFO;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						if (!serviceList.isEmpty())
							serviceList.clear();
						if (null != data && data.length() > 0) {
							String characteristicservice = data
									.getString("CharacteristicService");
							Map<String, String> map = new HashMap<String, String>();
							map.put("tip", "特色服务");
							map.put("content", characteristicservice);
							String generalfacilities = data
									.getString("GeneralFacilities");
							Map<String, String> map1 = new HashMap<String, String>();
							map1.put("tip", "设施服务");
							map1.put("content", generalfacilities);
							String trafficconditions = data
									.getString("TrafficConditions");
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put("tip", "交通状况");
							map2.put("content", trafficconditions);
							String contactus = data.getString("Contactus");
							Map<String, String> map3 = new HashMap<String, String>();
							map3.put("tip", "联系我们");
							map3.put("content", contactus);
							serviceList.add(map);
							serviceList.add(map1);
							serviceList.add(map2);
							serviceList.add(map3);
						}
					}
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void reservation(final RESERVATION res)
	{
		String url = ProtocolConst.RESERCATION;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						if(res.payType.equals("1"))
						{
						JSONObject data = jo.optJSONObject("data");
						CommonUtils.showToast(mContext, "提交订单成功");
						RoomModel.this.OnMessageResponse(url, jo, status);
						}else if(res.payType.equals("2"))
						{
							String data = jo.optString("data");
							confirm(data);
						}
					} 
//					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Member_ID", res.mId);
		params.put("PayType", res.payType);
		params.put("CustomerName", res.mName);
		params.put("ContactNumber", res.mPhone);
		params.put("Hotel_ID",res.hId);
		params.put("Room_type_ID", res.roomTpyeId);
		params.put("roomCount", res.number);
		params.put("BookingAmount", res.amount);
		params.put("CheckInDate", res.checkInDate);
		params.put("CheckOutDate", res.checkOutDate);
		params.put("Remarks", res.remark);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void getTotle(String mId,String hId,String typeId,String roomCount,String checkIn,String checkOut)
	{
		String url = ProtocolConst.TOTEL;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						if(null==data)
						{
							CommonUtils.showToast(mContext, "你填写的房间数量不正确");
							totle = "0";
						}else
						{
						totle =  data.getString("room_price");
						}
					} 
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Member_ID", mId);
		params.put("Hotel_ID", hId);
		params.put("Room_type_ID", typeId);
		params.put("roomCount", roomCount);
		params.put("CheckInDate",checkIn);
		params.put("CheckOutDate", checkOut);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void collection(String id)
	{
		String url = ProtocolConst.COLLECTION;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						CommonUtils.showToast(mContext, "收藏成功");
					}else
					{
						CommonUtils.showToast(mContext, "收藏失败");	
					}
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid",CommonUtils.getSharedPreFerences(mContext, CommonUtils.SHARED_USER_NAME));
		params.put("hotel_id", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
	public void confirm(String id)
	{
		String url = ProtocolConst.CONFIRM+id;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");
					if (responseStatus.toLowerCase().equals("ok")) {
					pay_url = jo.optString("data");
					}
					
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);
	}
	
	
	public void hotelGallery(String id)
	{
		String url = ProtocolConst.GALLERY;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");
					if (responseStatus.equals("ok")) {
					JSONArray data = jo.optJSONArray("data");
					if (!imageList.isEmpty())
						imageList.clear();
					if (null != data && data.length() > 0) {
						for (int i = 0; i < data.length(); i++) {
							JSONObject items = data.getJSONObject(i);
							imageList.add(items.getString("Picture"));
						}
					}
					}
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	public void orderDetail(String id)
	{
		String url = ProtocolConst.ORDER_INFO;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");
					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						orderDetail.put("name", data.getString("Hotel_Name"));
						orderDetail.put("count", data.getString("BookingRoomCount"));
						orderDetail.put("checkin", data.getString("CheckInDate"));
						orderDetail.put("checkout", data.getString("CheckOutDate"));
						orderDetail.put("amount", data.getString("BookingAmount"));
						orderDetail.put("remark", data.getString("Remark1"));
						orderDetail.put("custormer", data.getString("CustomerName"));
						orderDetail.put("phone", data.getString("ContactNumber"));
						orderDetail.put("paytype", data.getString("PayType_Name"));
						orderDetail.put("state", data.getString("Remark3"));
					}
					
					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}

	public void cancelOrder(String id){
		String url = ProtocolConst.ORDER_EXIT;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RoomModel.this.callback(url, jo, status);
				try {
//					String responseStatus = jo.optString("status");
//					if (responseStatus.toLowerCase().equals("ok")) {
////						pay_url = jo.optString("data");
//					}

					RoomModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
}
