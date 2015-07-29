package com.hhinns.library;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
	private DisplayUtils() {
	}

	/**
	 * 将dp转换为px
	 * 
	 * @param context
	 *            上下文
	 * @param baseSize
	 *            基准尺寸
	 * @return 适配当前设备密度的尺寸
	 */
	public static int dp2Px(Context context, int size) {
		return size * getDensityDpi(context) / DisplayMetrics.DENSITY_DEFAULT;
	}

	/**
	 * 获取当前设备的屏幕密度
	 * 
	 * @param context
	 *            上下文
	 * @return 屏幕密度
	 */
	public static int getDensityDpi(Context context) {
		int result = DisplayMetrics.DENSITY_DEFAULT;
		if (context instanceof Activity) {
			DisplayMetrics metric = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);

			result = metric.densityDpi;
		}
		return result;
	}
}
