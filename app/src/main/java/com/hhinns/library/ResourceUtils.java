package com.hhinns.library;

import android.content.Context;

public class ResourceUtils {
	private ResourceUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据Drawable名称获取资源标识
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            资源名称
	 * @return 标识
	 */
	public static int getDrawableIdentifier(Context context, String name) {
		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
	}
}
