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
import com.hhinns.dataprocess.HashMapCallBack;
import com.hhinns.library.CommonUtils;

public class ProccessForFavorable extends BaseNewModel {
	
	public List<Map<String, String>>favorableList = null;
	public static  final  int pages = 10;
	private boolean mIsfirst = false;
	private Context mContext;
	public ProccessForFavorable(Context context) {
		super(context);
		mContext = context;
		favorableList = new ArrayList<Map<String,String>>();
	}
	
	public void favorableNew(boolean isfirst)
	{
		mIsfirst = isfirst;
		int index = (int) Math.ceil((double) favorableList.size() * 1.0
				/ pages) + 1;
		 String url=String.format("?c=favorable_activities" +
				"&a=get_favorable_activities&pageIndex=%s&pageSize=%s"
				,index,10);
		aq.ajax(url, JSONObject.class, this, "callback");
//		new AsyncGetData(mContext).doMethod(new HashMapCallBack() {
//			
//			@Override
//			public void successHandler(JSONObject obj, AjaxStatus status) {
//				// TODO Auto-generated method stub
//				try {
//					if(null==obj)
//						return;
//					String responseStatus = obj.optString("status");
//
//					if (responseStatus.equals("ok")) {
//						JSONArray data = obj.optJSONArray("data");
//						
//						if(mIsfirst)
//						{
//							favorableList.clear();
//						}
//						if (null != data && data.length() > 0) {
//							Map<String, String> map = null;
//							for (int i = 0; i < data.length(); i++) {
//								JSONObject items = data.getJSONObject(i);
//								map.put("id", items.getString("id"));
//								map.put("name",
//										items.getString("name"));
//								map.put("price",
//										items.getString("pic"));
//								map.put("content",
//										items.getString("content"));
//								map.put("wap",
//										items.getString("url"));
//								favorableList.add(map);
//							}
//						}
//					}
//					
//					ProccessForFavorable.this.OnMessageResponse(url, obj, status);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			@Override
//			public void errorHandler() {
//				// TODO Auto-generated method stub
//				
//			}
//		}, url);
	}
	
	public void callback(String url, JSONObject obj, AjaxStatus status)
	{
		try {
			if(null==obj)
			{
				ProccessForFavorable.this.OnMessageResponse(url, obj, status);
			}
			String responseStatus = obj.optString("status");

			if (responseStatus.equals("ok")) {
				if(null!=obj.optJSONArray("data"))
				{
				JSONArray data = obj.optJSONArray("data");
				
				if(mIsfirst)
				{
					favorableList.clear();
				}
				if (null != data && data.length() > 0) {
					Map<String, String> map = null;
					for (int i = 0; i < data.length(); i++) {
						JSONObject items = data.getJSONObject(i);
						map = new HashMap<String, String>();
						map.put("id", items.getInt("contentid")+"");
						map.put("name",
								items.getString("name"));
						map.put("price",
								items.getString("pic"));
						if(null!=items.getString("ac_time"))
						{
							String str = items.getString("ac_time");
							String str_list[]=str.split(" ");
						map.put("time",
								str_list[0]);
						}else
						{
							map.put("time",
									"");
						}
						map.put("content",
								items.getString("content"));
						map.put("wap",
								items.getString("url"));
						favorableList.add(map);
					}
				}
				}else
				{
					if(mIsfirst)
					{
						favorableList.clear();
					}
					Map<String, String> map = null;
					JSONObject items = obj.optJSONObject("data");
					map = new HashMap<String, String>();
					map.put("id", items.getInt("contentid")+"");
					map.put("name",
							items.getString("name"));
					map.put("price",
							items.getString("pic"));
					map.put("time",
							items.getString("ac_time"));
					map.put("content",
							items.getString("content"));
					map.put("wap",
							items.getString("url"));
					favorableList.add(map);
				}
			}
			
			ProccessForFavorable.this.OnMessageResponse(url, obj, status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
