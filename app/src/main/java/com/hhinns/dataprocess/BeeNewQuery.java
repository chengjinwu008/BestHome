package com.hhinns.dataprocess;

import java.util.Map;

import com.hhinns.library.CommonUtils;

import android.content.Context;


public class BeeNewQuery<T> extends AQuery {
	public BeeNewQuery(Context context) {
		super(context);
	}

	public <K> AQuery ajax(AjaxCallback<K> callback) {

		if (AppConst.environment() == AppConst.ENVIROMENT_MOCKSERVER) {
			MockServer.ajax(callback);
			return null;
		} else {
			String url = callback.getUrl();
			String absoluteUrl = null;
			absoluteUrl = getAbsoluteUrl_new(url);

			callback.url(absoluteUrl);

		}

		if (AppConst.environment() == AppConst.ENVIROMENT_DEVELOPMENT) {
			DebugMessageModel.addMessage((BeeCallback) callback);
		}

		return (BeeNewQuery) super.ajax(callback);
	}

	public <K> AQuery ajax_new(AjaxCallback<K> callback) {

		if (AppConst.environment() == AppConst.ENVIROMENT_MOCKSERVER) {
			MockServer.ajax(callback);
			return null;
		} else {
			String url = callback.getUrl();
			String absoluteUrl = getAbsoluteUrl_new(url);
			callback.url(absoluteUrl);

		}

		if (AppConst.environment() == AppConst.ENVIROMENT_DEVELOPMENT) {
			DebugMessageModel.addMessage((BeeCallback) callback);
		}

		return (BeeNewQuery) super.ajax(callback);
	}

	public <K> AQuery ajaxAbsolute(AjaxCallback<K> callback) {

		return (BeeNewQuery) super.ajax(callback);
	}

	public <K> AQuery ajax(String url, Map<String, ?> params, Class<K> type,
			BeeCallback<K> callback) {

		callback.type(type).url(url).params(params);

		if (AppConst.environment() == AppConst.ENVIROMENT_MOCKSERVER) {
			MockServer.ajax(callback);
			return null;
		} else {
			String absoluteUrl = getAbsoluteUrl(url);
			callback.url(absoluteUrl);
		}
		return ajax(callback);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return CommonUtils.BASE_URL + relativeUrl;
	}

	private static String getAbsoluteUrl_new(String relativeUrl) {
		return CommonUtils.NEW_BASE_URL + relativeUrl;
	}
}