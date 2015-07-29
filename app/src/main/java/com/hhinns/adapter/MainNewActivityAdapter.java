package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hhinns.main.R;
import com.hhinns.view.GridViewSelfAdaption;

public class MainNewActivityAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<Map<String, String>> mList;
	private Activity mContext;


	public MainNewActivityAdapter(Activity context, List<Map<String, String>> list) {
		mList = list;
		mInflater = LayoutInflater.from(context);
		mContext=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if(null==convertView)
        {
        	convertView = (GridViewSelfAdaption)LayoutInflater.from(mContext).inflate(
					R.layout.item_gridview, null);	
        }
        ((GridViewSelfAdaption)convertView).bindData(mContext,position);
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(0==mList.size()%3)
		{
			return mList.size()/3;
		}else
		{
			return mList.size()/3+1;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
