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

public class ProccessForPhone extends BaseNewModel {
	
	public static String phoneNum;
	public static String andUrl = "";
	public static String ioUrl = "";
	public ProccessForPhone(Context context) {
		super(context);
	}
	
	public void getPhone()
	{
		String url="?c=hoteltel&a=gethoteltel";
		aq.ajax(url, JSONObject.class, this, "callback");
	}
	
	public void callback(String url, JSONObject obj, AjaxStatus status)
	{
		try {
			if(null==obj)
				return;
//			ProccessForNearBy.this.callback(url, obj, status);
			String responseStatus = obj.optString("status");

			if (responseStatus.equals("ok")) {
					JSONArray items = obj.optJSONArray("data");
					JSONObject  item_one = items.getJSONObject(0);
					phoneNum = item_one.getString("hotelphone");
					andUrl = item_one.getString("anddownloadurl");
					ioUrl = item_one.getString("iosdownloadurl");
			}
			ProccessForPhone.this.OnMessageResponse(url, obj, status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
