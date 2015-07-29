package com.hhinns.dataprocess;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.hhinns.library.CommonUtils;

public class AsyncGetData {

	private Context context;
	private HashMapCallBack mCallBack;
	private int counter = 3;

	public AsyncGetData(Context c) {
		this.context = c;
	}

	/**
	 * 寮傛銮峰彇鏁版嵁銆?
	 * 
	 * @param callBack
	 * @param url
	 *            鎺ュ彛鍦板潃
	 */
	public void doMethod(HashMapCallBack callBack, String url) {
		this.mCallBack = callBack;
		if (CommonUtils.networkIsAvaiable(context)) {
			new AsyncHandleData().execute(url);
		} else {
			mCallBack.errorHandler();
		}
	}

	private class AsyncHandleData extends AsyncTask<String, Integer, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject res = null;
			try {
				res = new InteractiveHandler(context).httpGetRequest(params[0]);
				//res = DataAccess.HandlerByHttpAddress(params[0]);
				return res;
			} catch (Exception ex) {
				System.out.println("AsyncGetData:doInBackground ---------"
						+ ex.getMessage());
//				int error = HandleHttpException.setExceptionFlag(ex);
				res = repeatGetData( counter, params[0]);
				return res;
			} finally {
				res = null;
			}

		}

		@Override
		protected void onPostExecute(JSONObject result) {
			if (result == null || result.length() == 0) {
				mCallBack.errorHandler();
				return;
			}
			
			mCallBack.successHandler(result, new AjaxStatus());
			
		}
	}

	/**
	 * 鍐嶆阈炬帴銆?
	 * 
	 * @param context
	 * @param error
	 * @param counter
	 * @param res
	 * @param url
	 * @return
	 */
	private JSONObject repeatGetData( int counter, String url) {
		JSONObject res = null;
//		if (error == HandleHttpException.SOCKET_TIMEOUT_EXCEPTION
//				|| error == HandleHttpException.CONNECTION_TIMEOUT_EXCEPTION) {
			if (counter >= 0) {
				counter--;
				System.out.println("Socket or connection timeout:" + counter);
				try {
					res = new InteractiveHandler(context).httpGetRequest(url);;
				} catch (Exception ex) {
					
					res = repeatGetData(counter, url);
				}
			} else {
				res = null;
			}
//		}
		return res;
	}

}