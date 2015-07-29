package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhinns.main.R;

public class HotelListActivityAdapter extends BaseViewAdapter {
	private LayoutInflater mInflater = null;
	private ViewHolder mViewHolder = null;

	private class ViewHolder {
		private TextView mTvHotelName;
		private TextView mTvRoomState;
		private TextView mTvAddress;
		private TextView mTvPrice;

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

	public HotelListActivityAdapter(Context context,
			List<Map<String, String>> list) {
		super(list);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, String> hashMap = null;

		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_hotel_list_item,
					null);
			mViewHolder.setmTvHotelName((TextView) convertView
					.findViewById(R.id.tvHotelName));
			mViewHolder.setmTvRoomState((TextView) convertView
					.findViewById(R.id.tvRoomState));
			mViewHolder.setmTvAddress((TextView) convertView
					.findViewById(R.id.tvAddress));
			mViewHolder.setmTvPrice((TextView) convertView
					.findViewById(R.id.tvPrice));

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
		}
		return convertView;
	}
}
