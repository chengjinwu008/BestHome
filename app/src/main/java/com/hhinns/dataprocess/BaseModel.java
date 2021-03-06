package com.hhinns.dataprocess;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.library.CommonUtils;
import com.hhinns.main.R;
import com.hhinns.view.ToastView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.Gravity;


public class BaseModel implements BusinessResponse {

	protected BeeQuery aq;
	protected ArrayList<BusinessResponse> businessResponseArrayList = new ArrayList<BusinessResponse>();
	protected Context mContext;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	public BaseModel() {

	}

	public BaseModel(Context context) {
		aq = new BeeQuery(context);
		mContext = context;
	}

	protected void saveCache() {
		return;
	}

	public boolean checkNet(Context mContext) {
		boolean network = true;
		if (!CommonUtils.networkIsAvaiable(mContext)) {
			ToastView toast = new ToastView(mContext,
					mContext.getString(R.string.network_unwork));
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			network = false;
		}
		return network;
	}

	protected void cleanCache() {
		return;
	}

	public void addResponseListener(BusinessResponse listener) {
		if (!businessResponseArrayList.contains(listener)) {
			businessResponseArrayList.add(listener);
		}
	}

	public void removeResponseListener(BusinessResponse listener) {
		businessResponseArrayList.remove(listener);
	}

	public void callback(String url, JSONObject jo, AjaxStatus status) {
		try {
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		for (BusinessResponse iterable_element : businessResponseArrayList) {
			iterable_element.OnMessageResponse(url, jo, status);
		}
	}
}
