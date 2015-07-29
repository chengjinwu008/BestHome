package com.hhinns.dataprocess;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

/**
 * <p>
 * 锲炶皟鎺ュ彛 - List<HashMap<String,String>>
 * </p>
 * Created By Eric at 2012-7-5.
 */
public interface HashMapCallBack {

	public void successHandler(JSONObject obj, AjaxStatus status);

	public void errorHandler();

}
