package com.hhinns.adapter;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class Bee_PageAdapter extends PagerAdapter {
	public List<View> mListViews;

	public Bee_PageAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));// 鍒犻櫎椤靛崱
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 杩欎釜鏂规硶鐢ㄦ潵瀹炰緥鍖栭〉鍗?
		container.addView(mListViews.get(position), 0);// 娣诲姞椤靛崱
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews.size();// 杩斿洖椤靛崱镄勬暟閲?
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 瀹樻柟鎻愮ず杩欐牱鍐?
	}

}
