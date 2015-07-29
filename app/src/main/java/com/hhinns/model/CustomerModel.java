package com.hhinns.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BaseModel;
import com.hhinns.dataprocess.BeeCallback;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;

public class CustomerModel extends BaseModel{

	private SharedPreferences preferences = null;
	private SharedPreferences.Editor editor;
	public Map<String, String> customer = null;
	public String code = null;
	public CustomerModel(Context context)
	{
		super(context);
		preferences = context.getSharedPreferences(CommonUtils.SHARED_NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
		customer = new HashMap<String, String>();
	}
	
	public void Login(String name,String pwd)
	{
		String url = ProtocolConst.LOGIN;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						editor.putString(CommonUtils.SHARED_USER_NAME, data.getString("MemberNumber"));
						editor.putString(CommonUtils.SHARED_USER_NICKNAME,data.getString("CustomerName"));
						editor.putString(CommonUtils.SHARED_USER_PHONE,data.getString("MobilePhone"));
						editor.commit();
					}else
					{
						CommonUtils.showToast(mContext, "用户名或密码错误");
					}
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", name);
		params.put("password", pwd);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	public void Register(String name,String pwd,String mobile,String code)
	{
		String url = ProtocolConst.REGISTER;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						editor.putString(CommonUtils.SHARED_USER_NAME, data.getString("MemberNumber"));
						editor.commit();
					} 
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("password", pwd);
		params.put("mobile", mobile);
		params.put("code", code);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void getAuthcode(String phonenumber)
	{
		String url = ProtocolConst.SMSCODE;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						code = data.getString("keycode");
					} 
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phonenum", phonenumber);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void CustomerInfo()
	{
		String url = ProtocolConst.FETCH_MEMINFO;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
						customer.put("name", data.getString("Customer_Name"));
						customer.put("phone", data.getString("MobilePhone"));
						customer.put("email", data.getString("Email"));
						customer.put("sex", data.getString("Sex"));
						customer.put("idcard", data.getString("IDCard"));
					} 
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", CommonUtils.getSharedPreFerences(mContext, CommonUtils.SHARED_USER_NAME));
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	
	public void changePwd(String old,String newp)
	{
		String url = ProtocolConst.UPDATE_PWD;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						JSONObject data = jo.optJSONObject("data");
					} 
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", CommonUtils.getSharedPreFerences(mContext, CommonUtils.SHARED_USER_NAME));
		params.put("oldpwd", old);
		params.put("newpwd", newp);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	public void changeCustomer(final String name,final String mobile,String sex,String cardid,String email)
	{
		String url = ProtocolConst.UPDATE_MEMINFO;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						editor.putString(CommonUtils.SHARED_USER_NICKNAME,name);
						editor.putString(CommonUtils.SHARED_USER_PHONE,mobile);
						editor.commit();
						CommonUtils.showToast(mContext, "修改个人资料成功");
						JSONObject data = jo.optJSONObject("data");
					} 
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", CommonUtils.getSharedPreFerences(mContext, CommonUtils.SHARED_USER_NAME));
		params.put("username", name);
		params.put("mobile", mobile);
		params.put("sex", sex);
		params.put("cardid", cardid);
		params.put("email", email);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
	
	public void FindPwd(String phone)
	{
		String url = ProtocolConst.FIND_PWD;
		if (!super.checkNet(mContext))
			return;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CustomerModel.this.callback(url, jo, status);
				try {
					String responseStatus = jo.optString("status");

					if (responseStatus.equals("ok")) {
						CommonUtils.showToast(mContext, "提交申请成功请等待消息");
					}else
					{
						CommonUtils.showToast(mContext, "手机号不存在");	
					}
					CustomerModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userid", CommonUtils.getSharedPreFerences(mContext, CommonUtils.SHARED_USER_NAME));
		params.put("phonenumber", phone);
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
}
