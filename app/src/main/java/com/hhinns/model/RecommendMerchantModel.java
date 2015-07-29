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

public class RecommendMerchantModel extends BaseModel{

	public List<Map<String, String>> hotelList = null;
	public static final int pages = 10;
	public RecommendMerchantModel(Context context)
	{
		super(context);
		hotelList = new ArrayList<Map<String,String>>();
	}
	
	public void searchHotel()
	{
		String url = ProtocolConst.RECOMMEND_MERCHANT;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RecommendMerchantModel.this.callback(url, jo, status);
				try {
					if(jo==null)
					{
						RecommendMerchantModel.this.OnMessageResponse(url, jo, status);
					}
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						if(null!=jo.optJSONArray("data"))
						{
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
							 map.put("price", items.getString("room_price"));
							 map.put("name", items.getString("Hotel_Name"));
							 map.put("lon", items.getString("Lon"));
							 map.put("lat", items.getString("Lat"));
							 map.put("id", items.getString("Hotel_id"));
							 map.put("addre", items.getString("Address"));
						     map.put("state", "有房");
							 hotelList.add(map);
							}
						}
						}else
						{
							if(!hotelList.isEmpty())
								hotelList.clear();
							Map <String, String> map = null;
							JSONObject items = jo.optJSONObject("data");
							 map = new HashMap<String, String>();
							 map.put("price", items.getString("room_price"));
							 map.put("name", items.getString("Hotel_Name"));
							 map.put("lon", items.getString("Lon"));
							 map.put("lat", items.getString("Lat"));
							 map.put("id", items.getString("Hotel_id"));
							 map.put("addre", items.getString("Address"));
						     map.put("state", "有房");
							 hotelList.add(map);
							
						}
					} 
					RecommendMerchantModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageIndex", "1");
		params.put("pageSize", pages);
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
	public void searchHotelMore()
	{
		String url = ProtocolConst.RECOMMEND_MERCHANT;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RecommendMerchantModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						if(null!=jo.optJSONArray("data"))
						{
						JSONArray data = jo.optJSONArray("data");
						if(null!=data&&data.length()>0)
						{
							Map <String, String> map = null;
							for(int i=0;i<data.length();i++)
							{
								JSONObject items = data.getJSONObject(i);
								 map = new HashMap<String, String>();
								map.put("price", items.getString("room_price"));
								 map.put("name", items.getString("Hotel_Name"));
								 map.put("lon", items.getString("Lon"));
								 map.put("lat", items.getString("Lat"));
								 map.put("id", items.getString("Hotel_id"));
								 map.put("addre", items.getString("Address"));
							     map.put("state", "有房");
								 hotelList.add(map);
							}
						}
						}else
						{
							Map <String, String> map = null;
							JSONObject items = jo.optJSONObject("data");
							 map = new HashMap<String, String>();
							map.put("price", items.getString("room_price"));
							 map.put("name", items.getString("Hotel_Name"));
							 map.put("lon", items.getString("Lon"));
							 map.put("lat", items.getString("Lat"));
							 map.put("id", items.getString("Hotel_id"));
							 map.put("addre", items.getString("Address"));
						     map.put("state", "有房");
							 hotelList.add(map);
						}
					} 
					RecommendMerchantModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		int index = (int) Math.ceil((double) hotelList.size() * 1.0
				/ pages) + 1;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageIndex", index);
		params.put("pageSize", pages);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}

	
	
	
	
}
