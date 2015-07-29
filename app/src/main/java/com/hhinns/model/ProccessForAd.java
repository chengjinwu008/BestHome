package com.hhinns.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.AsyncGetData;
import com.hhinns.dataprocess.BaseModel;
import com.hhinns.dataprocess.BaseNewModel;
import com.hhinns.dataprocess.BeeCallback;
import com.hhinns.dataprocess.HashMapCallBack;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.main.MainActivity;

public class ProccessForAd extends BaseNewModel {
	
	public ArrayList<HashMap<String, String>> adList;
	public ProccessForAd(Context context) {
		super(context);
		adList = new ArrayList<HashMap<String,String>>();
	}
	
	public void getAdd()
	{
		String url="?c=advertisement&a=get_advertisements";
		aq.ajax(url, JSONObject.class, this, "callback");
	}
	
	public void callback(String url, JSONObject obj, AjaxStatus status)
	{
		try {
			if(null==obj)
				return;
			String responseStatus = obj.optString("status");

			if (responseStatus.equals("ok")) {
					JSONArray data = obj.optJSONArray("data");
					if (!adList.isEmpty())
						adList.clear();
					if (null != data && data.length() > 0) {
						HashMap<String, String> map = null;
						for (int i = 0; i < data.length(); i++) {
							map = new HashMap<String, String>();
							JSONObject items = data.getJSONObject(i);
							map.put("id", items.getString("contentid"));
							map.put("content",
									items.getString("content"));
							map.put("name",
									items.getString("name"));
							map.put("time",
									items.getString("actime"));
							map.put("pic",
									items.getString("pic"));
							map.put("url",
									items.getString("url"));
							adList.add(map);
						}			
			}
			}
			ProccessForAd.this.OnMessageResponse(url, obj, status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
