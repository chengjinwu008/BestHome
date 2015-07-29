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

public class AboutModel extends BaseModel{

	public String about = null;
	public AboutModel(Context context)
	{
		super(context);
	}
	
	public void about()
	{
		String url = ProtocolConst.ABOUT_US;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				AboutModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						String data = jo.optString("data");
								about = data;
					} 
					AboutModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);
	}
	
	
}
