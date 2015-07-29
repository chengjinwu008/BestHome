package com.hhinns.library;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapCache {
	private int mMaxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
	private int mCacheSize = mMaxSize / 8;

	private final LruCache<String, Bitmap> mBitmapCache = new LruCache<String, Bitmap>(
			mCacheSize) {

		@Override
		protected int sizeOf(String key, Bitmap value) {
			int size = 0;
			if (null != value) {
				size = value.getRowBytes() * value.getHeight() / 1024;
			}
			return size;
		}
	};

	public void addDataToCache(String key, Bitmap value) {
		if (null != value) {
			synchronized (mBitmapCache) {
				mBitmapCache.put(key, value);
			}
		}
	}
	
	public Bitmap getBitmapFromCache(String key) {
		return mBitmapCache.get(key);
	}
}
