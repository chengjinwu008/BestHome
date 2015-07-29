package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import com.hhinns.bitmap.BitmapCommonUtils;
import com.hhinns.bitmap.BitmapDisplayConfig;
import com.hhinns.library.BitmapUtils;
import com.hhinns.main.R;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BaseViewAdapter extends BaseAdapter {
	protected List<Map<String, String>> mList = null;
	public Context mContext;
	private BitmapDisplayConfig con;
	private BitmapUtils bitmap ;

	public BaseViewAdapter(List<Map<String, String>> list ) {
		mList = list;
	}
	
	public BaseViewAdapter(List<Map<String, String>> list ,Context mContext) {
		mList = list;
		this.mContext = mContext;
		bitmap = new BitmapUtils(mContext);
		con = new BitmapDisplayConfig();
		con.setLoadingDrawable(mContext.getResources().getDrawable(
				R.drawable.ic_launcher));
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	public void downloadImage(ImageView imgView, String url) {
		bitmap.display(imgView, url, con);
	}
}
