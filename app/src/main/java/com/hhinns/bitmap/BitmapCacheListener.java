package com.hhinns.bitmap;

/**
 * Created with IntelliJ IDEA. User: wyouflf Date: 13-10-16 Time: 涓嫔崃4:26
 */
public interface BitmapCacheListener
{
	void onInitMemoryCacheFinished();

	void onInitDiskFinished();

	void onClearCacheFinished();

	void onClearMemoryCacheFinished();

	void onClearDiskCacheFinished();

	void onClearCacheFinished(String uri);

	void onClearMemoryCacheFinished(String uri);

	void onClearDiskCacheFinished(String uri);

	void onFlushCacheFinished();

	void onCloseCacheFinished();
}
