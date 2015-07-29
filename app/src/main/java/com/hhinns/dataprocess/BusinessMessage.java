package com.hhinns.dataprocess;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.hhinns.main.R;


public class BusinessMessage {
	public static final int FAILURE_MESSAGE = 1;
	public static final int SENDING_MESSAGE = 2;
	public static final int CANCEL_MESSAGE = 3;

	public String messageString;
	public JSONObject response;
	public Map<String, Object> requestParams;
	public int messageState;

	public String timeStamp;

	List<NameValuePair> mParametersList;

	public static Stack<BusinessMessage> messageList = new Stack<BusinessMessage>();
	public static ArrayList<BusinessMessage> sendingMessageList = new ArrayList<BusinessMessage>();
	private Context mContext;

	public BusinessMessage(Context context, String msg, int MessageState,
			Map<String, Object> requestParams, JSONObject responseDataJsonObject) {
		mContext = context;
		this.messageString = msg;
		this.messageState = MessageState;
		this.requestParams = requestParams;
		this.response = responseDataJsonObject;
		long currentTimestamp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat(
				context.getString(R.string.time_format_s));
		this.timeStamp = sdf.format(new Date(currentTimestamp));

		BusinessMessage.addMessage(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String msgDesc = "";
		msgDesc += mContext.getString(R.string.time_s) + this.timeStamp
				+ "\n\n";
		msgDesc += mContext.getString(R.string.message_s) + this.messageString
				+ "\n\n";
		msgDesc += mContext.getString(R.string.request_s)
				+ this.requestParams.toString() + "\n\n";

		if (null != this.response) {
			msgDesc += mContext.getString(R.string.response_s)
					+ this.response.toString();
		}

		return msgDesc;
	}

	public static void addMessage(BusinessMessage msg) {
		messageList.push(msg);
		sendingMessageList.add(msg);
	}

	public static void finishMessage(BusinessMessage msg) {
		sendingMessageList.remove(msg);
	}

	public static boolean isSendingMessage(String url) {
		for (int i = 0; i < sendingMessageList.size(); i++) {
			BusinessMessage msg = sendingMessageList.get(i);
			if (msg.messageState == BusinessMessage.SENDING_MESSAGE
					&& 0 == msg.messageString.compareToIgnoreCase(url)) {
				return true;
			}
		}

		return false;
	}
}
