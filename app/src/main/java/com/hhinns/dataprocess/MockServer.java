package com.hhinns.dataprocess;

import org.json.JSONObject;

public class MockServer {
	private static MockServer instance;

	public static MockServer getInstance() {
		if (instance == null) {
			instance = new MockServer();
		}
		return instance;
	}

	public static <K> void ajax(AjaxCallback<K> callback) {
		JSONObject responseJsonObject = new JSONObject();

		((BeeCallback) callback).callback(callback.getUrl(),
				responseJsonObject, callback.getStatus());
	}
}
