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

public class CityModel extends BaseModel{

	public List<Map<String, String>> cityList = null;
	public List<Map<String, String>> areaList = null;
	public CityModel(Context context)
	{
		super(context);
		cityList = new ArrayList<Map<String,String>>();
		areaList = new ArrayList<Map<String,String>>();
	}
	
	public void fetchCity()
	{
		String url = ProtocolConst.FETCH_CITY;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CityModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONArray data = jo.optJSONArray("data");
						if(!cityList.isEmpty())
							cityList.clear();
						if(null!=data&&data.length()>0)
						{
							Map <String, String> map = null;
							for(int i=0;i<data.length();i++)
							{
							JSONObject items = data.getJSONObject(i);
							 map = new HashMap<String, String>();
							 map.put("id", items.getString("CityId"));
							 map.put("name", items.getString("City"));
							 cityList.add(map);
							}
						}
					} 
					CityModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);
	}
	
	public void fetchShopArea(String id)
	{
		String url = ProtocolConst.FETCH_SHOPAREA;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CityModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONArray data = jo.optJSONArray("data");
						if(!areaList.isEmpty())
							areaList.clear();
						if(null!=data&&data.length()>0)
						{
							Map <String, String> map = null;
							for(int i=0;i<data.length();i++)
							{
							JSONObject items = data.getJSONObject(i);
							 map = new HashMap<String, String>();
							 map.put("id", items.getString("AreaId"));
							 map.put("name", items.getString("Area"));
							 areaList.add(map);
							}
						}
					} 
					CityModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityid", id);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
	
}
