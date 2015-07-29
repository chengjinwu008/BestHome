package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhinns.library.RoomCallBack;
import com.hhinns.main.HotelCollectListActivity;
import com.hhinns.main.R;

public class CollectListActivityAdapter extends BaseViewAdapter {
	private LayoutInflater mInflater = null;
	private ViewHolder mViewHolder = null;
	private RoomCallBack callBack = null;

	private class ViewHolder {
		private TextView mTvHotelName;
		private TextView mTvRoomState;
		private TextView mTvAddress;
		private TextView mTvPrice;
		private LinearLayout mLinDelete;
		private LinearLayout mLinInfo;
		

		public LinearLayout getmLinInfo() {
			return mLinInfo;
		}

		public void setmLinInfo(LinearLayout mLinInfo) {
			this.mLinInfo = mLinInfo;
		}

		public LinearLayout getmLinDelete() {
			return mLinDelete;
		}

		public void setmLinDelete(LinearLayout mLinDelete) {
			this.mLinDelete = mLinDelete;
		}

		public TextView getmTvHotelName() {
			return mTvHotelName;
		}

		public void setmTvHotelName(TextView mTvHotelName) {
			this.mTvHotelName = mTvHotelName;
		}

		public TextView getmTvRoomState() {
			return mTvRoomState;
		}

		public void setmTvRoomState(TextView mTvRoomState) {
			this.mTvRoomState = mTvRoomState;
		}

		public TextView getmTvAddress() {
			return mTvAddress;
		}

		public void setmTvAddress(TextView mTvAddress) {
			this.mTvAddress = mTvAddress;
		}

		public TextView getmTvPrice() {
			return mTvPrice;
		}

		public void setmTvPrice(TextView mTvPrice) {
			this.mTvPrice = mTvPrice;
		}
	}

	public CollectListActivityAdapter(Context context,
			List<Map<String, String>> list,RoomCallBack callback) {
		super(list);
		mInflater = LayoutInflater.from(context);
		callBack = callback;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Map<String, String> hashMap = null;

		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_collec_list_item,
					null);
			mViewHolder.setmTvHotelName((TextView) convertView
					.findViewById(R.id.tvHotelName));
			mViewHolder.setmTvRoomState((TextView) convertView
					.findViewById(R.id.tvRoomState));
			mViewHolder.setmTvAddress((TextView) convertView
					.findViewById(R.id.tvAddress));
			mViewHolder.setmTvPrice((TextView) convertView
					.findViewById(R.id.tvPrice));
			mViewHolder.setmLinDelete((LinearLayout) convertView
					.findViewById(R.id.lin_delet));
			mViewHolder.setmLinInfo((LinearLayout) convertView
					.findViewById(R.id.lin_info));

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		hashMap = mList.get(position);
		if (null != hashMap && !hashMap.isEmpty()) {
			mViewHolder.getmTvHotelName().setText(hashMap.get("name"));
			mViewHolder.getmTvRoomState().setText(hashMap.get("state"));
			mViewHolder.getmTvAddress().setText(hashMap.get("addre"));
			mViewHolder.getmTvPrice().setText("￥"+hashMap.get("price"));
			if(HotelCollectListActivity.isDelet)
			{
				mViewHolder.getmLinDelete().setVisibility(View.VISIBLE);
				mViewHolder.getmLinInfo().setVisibility(View.GONE);
				mViewHolder.getmLinDelete().setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						callBack.callBack(position);
					}
				});
			}else
			{
				mViewHolder.getmLinInfo().setVisibility(View.VISIBLE);
				mViewHolder.getmLinDelete().setVisibility(View.GONE);
			}
		}
		return convertView;
	}
}
