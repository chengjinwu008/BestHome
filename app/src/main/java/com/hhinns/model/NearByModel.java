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

public class NearByModel extends BaseModel{

	public List<Map<String, String>> hotelList = null;

	public NearByModel(Context context)
	{
		super(context);
		hotelList = new ArrayList<Map<String,String>>();
	}
	
	public void near(String name)
	{
		String url = ProtocolConst.NEAR;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@SuppressWarnings("unused")
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				NearByModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");
                       if(jo==null)
                       {
                    	   NearByModel.this.OnMessageResponse(url, jo, status);
                    	   return;
                       }
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
							 map.put("id", items.getString("hotel_id"));
							 map.put("name", items.getString("hotel_name"));
							 map.put("price", items.getString("room_price"));
							 map.put("lat", items.getString("lat"));
							 map.put("lon", items.getString("lon"));
							 map.put("address", items.getString("Address"));
							 hotelList.add(map);
							}
						}
						}else
						{
							if(jo.optJSONObject("data")==null)
							{
								NearByModel.this.OnMessageResponse(url, jo, status);
								return;
							}
							if(!hotelList.isEmpty())
								hotelList.clear();
							Map <String, String> map = null;
							JSONObject items = jo.optJSONObject("data");
							 map = new HashMap<String, String>();
							 map.put("id", items.getString("hotel_id"));
							 map.put("name", items.getString("hotel_name"));
							 map.put("price", items.getString("room_price"));
							 map.put("lat", items.getString("lat"));
							 map.put("lon", items.getString("lon"));
							 hotelList.add(map);
						}
					} 
					NearByModel.this.OnMessageResponse(url, jo, status);
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityName", name);
		params.put("latitude",MainActivity.mLocation.latitude);
		params.put("longitude", MainActivity.mLocation.longitude);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
	
	
}
