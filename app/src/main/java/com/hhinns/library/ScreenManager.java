package com.hhinns.library;

import java.util.Stack;

import android.app.Activity;

public class ScreenManager {

	public static ScreenManager getScreenManager() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	private Activity currentActivity() {
		Activity activity = null;
		try {
			if (activityStack != null && !activityStack.isEmpty()) {
				activity = activityStack.pop();
			}
			return activity;
		} catch (Exception ex) {
			System.out.println("ScreenManager:currentActivity---->"
					+ ex.getMessage());
			return activity;
		} finally {
			activity = null;
		}
	}

	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.push(activity);
	}

	public void popAllActivity() {
		Activity activity = null;
		try {
			while (activityStack != null && !activityStack.isEmpty()) {
				activity = currentActivity();
				if (activity != null) {
					popActivity(activity);
				}
			}
		} catch (Exception ex) {
			System.out.println("ScreenManager:popAllActivity---->"
					+ ex.getMessage());
		} finally {
			activity = null;
		}
	}

	private ScreenManager() {

	}

	private static Stack<Activity> activityStack;
	private static ScreenManager instance;
}
