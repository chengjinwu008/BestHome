package com.hhinns.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BaseModel;
import com.hhinns.dataprocess.BeeCallback;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.main.MainActivity;
import com.hhinns.protocol.SEARCH;

public class SpecialPriceModel extends BaseModel{

	public List<Map<String, String>> hotelList = null;
	public static final int pages = 10;
	public SpecialPriceModel(Context context)
	{
		super(context);
		hotelList = new ArrayList<Map<String,String>>();
	}
	
	public void searchHotel(String date)
	{
		String url = ProtocolConst.SPECIAL_PRICE;
//		String url = "00000000000000/sn1234567891/12.15231/456.123/api/ver5/itemlist/news/";
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				SpecialPriceModel.this.callback(url, jo, status);
				try {
					if(jo==null)
					{
						SpecialPriceModel.this.OnMessageResponse(url, jo, status);
					}
					String responseStatus = jo.optString("status");
                        
					if (responseStatus.equals("ok")) {
						JSONArray data = jo.optJSONArray("data");
						if(!hotelList.isEmpty())
							hotelList.clear();
						if(null!=data&&data.length()>0)
						{
							Map <String, String> map = null;
							for(int i=0;i<data.length();i++)
							{
							JSONObject items = data.getJSONObject(i);
							 map = new HashMap<String, String>();
							 map.put("price", "￥"+items.getString("room_price"));
							 map.put("lon", items.getString("Lon"));
							 map.put("hotelName", items.getString("Hotel_Name"));
							 map.put("id", items.getString("Hotel_id"));
							 map.put("lat", items.getString("Lat"));
							 map.put("roomState", "有房");
							 map.put("info", items.getString("GeneralFacilities"));
							 map.put("count",items.getString("room_count"));
							 map.put("addr",items.getString("Address"));
							 hotelList.add(map);
							}
						}
					} 
					SpecialPriceModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchDate", date);
		params.put("latitude", MainActivity.mLocation.latitude);
		params.put("longitude", MainActivity.mLocation.longitude);
		params.put("pageIndex", "1");
		params.put("pageSize", pages);
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
//		aq.ajax(url, JSONObject.class, this, "callback");
	}
	
	
	public void searchHotelMore(String date)
	{
		String url = ProtocolConst.SPECIAL_PRICE;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				SpecialPriceModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONArray data = jo.optJSONArray("data");
						if(null!=data&&data.length()>0)
						{
							Map <String, String> map = null;
							for(int i=0;i<data.length();i++)
							{
								JSONObject items = data.getJSONObject(i);
								 map = new HashMap<String, String>();
								 map.put("price", items.getString("room_price"));
								 map.put("lon", items.getString("Lon"));
								 map.put("hotelName", items.getString("Hotel_Name"));
								 map.put("id", items.getString("Hotel_id"));
								 map.put("lat", items.getString("Lat"));
								 map.put("roomState", "有房");
								 map.put("info", items.getString("GeneralFacilities"));
								 map.put("count",items.getString("room_count"));
								 hotelList.add(map);
							}
						}
					} 
					SpecialPriceModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		int index = (int) Math.ceil((double) hotelList.size() * 1.0
				/ pages) + 1;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchDate", date);
		params.put("latitude", MainActivity.mLocation.latitude);
		params.put("longitude", MainActivity.mLocation.longitude);
		params.put("pageIndex", index);
		params.put("pageSize", pages);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
//	public void callback(String url, JSONObject obj, AjaxStatus status)
//	{
//		try {
//			if(null==obj)
//				return;
//			String responseStatus = obj.optString("status");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	
	
	
}
