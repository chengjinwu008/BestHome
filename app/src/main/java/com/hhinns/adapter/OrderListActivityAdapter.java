package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhinns.main.R;

public class OrderListActivityAdapter extends BaseViewAdapter {
	private LayoutInflater mInflater = null;
	private ViewHolder mViewHolder = null;

	private class ViewHolder {
		private TextView mTvHotelName;
		private TextView mTvCheckInDate;
		private TextView mTvDays;
		private TextView mTvPrice;
		private TextView mTvState;

		public TextView getmTvHotelName() {
			return mTvHotelName;
		}

		public void setmTvHotelName(TextView mTvHotelName) {
			this.mTvHotelName = mTvHotelName;
		}

		public TextView getmTvCheckInDate() {
			return mTvCheckInDate;
		}

		public void setmTvCheckInDate(TextView mTvCheckInDate) {
			this.mTvCheckInDate = mTvCheckInDate;
		}

		public TextView getmTvDays() {
			return mTvDays;
		}

		public void setmTvDays(TextView mTvDays) {
			this.mTvDays = mTvDays;
		}

		public TextView getmTvState() {
			return mTvState;
		}

		public void setmTvState(TextView mTvState) {
			this.mTvState = mTvState;
		}

		public TextView getmTvPrice() {
			return mTvPrice;
		}

		public void setmTvPrice(TextView mTvPrice) {
			this.mTvPrice = mTvPrice;
		}
	}

	public OrderListActivityAdapter(Context context,
			List<Map<String, String>> list) {
		super(list);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, String> hashMap = null;

		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_order_item,
					null);
			mViewHolder.setmTvHotelName((TextView) convertView
					.findViewById(R.id.tvTitle));
			mViewHolder.setmTvCheckInDate((TextView) convertView
					.findViewById(R.id.tvDate));
			mViewHolder.setmTvDays((TextView) convertView
					.findViewById(R.id.tvDays));
			mViewHolder.setmTvState((TextView) convertView
					.findViewById(R.id.tvType));
			mViewHolder.setmTvPrice((TextView) convertView
					.findViewById(R.id.tvPrice));

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		hashMap = mList.get(position);
		if (null != hashMap && !hashMap.isEmpty()) {
			mViewHolder.getmTvHotelName().setText(hashMap.get("name"));
             if(null!=hashMap.get("checkinDate"))
             {
            	 String checkin = hashMap.get("checkinDate");
            	 String str_list[] = checkin.split(" ");
			mViewHolder.getmTvCheckInDate().setText("入住时间:"+str_list[0]);
             }
			mViewHolder.getmTvDays().setText(hashMap.get("date")+"天");
			if(!hashMap.get("state").equals("未支付"))
			{
			mViewHolder.getmTvState().setText(hashMap.get("flag"));
			}else
			{
				mViewHolder.getmTvState().setText(hashMap.get("state"));	
			}
			mViewHolder.getmTvPrice().setText("￥"+hashMap.get("amount"));
		}
		return convertView;
	}
}
