package com.hhinns.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhinns.library.RoomCallBack;
import com.hhinns.main.R;
import com.hhinns.main.ReservationActivity;

public class RoomListFragmentAdapter extends BaseViewAdapter {
	private LayoutInflater mInflater = null;
	private ViewHolder mViewHolder = null;
	private RoomCallBack mCallBack;
	
	private class ViewHolder {
		private LinearLayout mLayContent;
		private TextView mTvPrice;
		private TextView mTvRoomType;
		private TextView mTvOperate;
		private LinearLayout mLayDes;
		private ImageView mIvPhoto;
		private TextView mTvDes;

		public LinearLayout getmLayContent() {
			return mLayContent;
		}

		public void setmLayContent(LinearLayout mLayContent) {
			this.mLayContent = mLayContent;
		}

		public TextView getmTvPrice() {
			return mTvPrice;
		}

		public void setmTvPrice(TextView mTvPrice) {
			this.mTvPrice = mTvPrice;
		}

		public TextView getmTvRoomType() {
			return mTvRoomType;
		}

		public void setmTvRoomType(TextView mTvRoomType) {
			this.mTvRoomType = mTvRoomType;
		}

		public TextView getmTvOperate() {
			return mTvOperate;
		}

		public void setmTvOperate(TextView mTvOperate) {
			this.mTvOperate = mTvOperate;
		}

		public LinearLayout getmLayDes() {
			return mLayDes;
		}

		public void setmLayDes(LinearLayout mLayDes) {
			this.mLayDes = mLayDes;
		}

		public ImageView getmIvPhoto() {
			return mIvPhoto;
		}

		public void setmIvPhoto(ImageView mIvPhoto) {
			this.mIvPhoto = mIvPhoto;
		}

		public TextView getmTvDes() {
			return mTvDes;
		}

		public void setmTvDes(TextView mTvDes) {
			this.mTvDes = mTvDes;
		}

	}

	public RoomListFragmentAdapter(Context context,
			List<Map<String, String>> list,RoomCallBack callback) {
		super(list, context);
		mCallBack = callback;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 Map<String, String> hashMap = null;

		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_room_list_item,
					null);
			mViewHolder.setmLayContent((LinearLayout) convertView
					.findViewById(R.id.layContent));
			mViewHolder.setmTvPrice((TextView) convertView
					.findViewById(R.id.tvPrice));
			mViewHolder.setmTvRoomType((TextView) convertView
					.findViewById(R.id.tvRoomType));
			mViewHolder.setmTvOperate((TextView) convertView
					.findViewById(R.id.tvOperate));
			mViewHolder.setmLayDes((LinearLayout) convertView
					.findViewById(R.id.layDes));
			mViewHolder.setmIvPhoto((ImageView) convertView
					.findViewById(R.id.ivPhoto));
			mViewHolder.setmTvDes((TextView) convertView
					.findViewById(R.id.tvDes));

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		hashMap = mList.get(position);
		if (null != hashMap && !hashMap.isEmpty()) {
			if(hashMap.get("special").equals("是"))
			{
				mViewHolder.getmTvPrice().setText("特价￥"+hashMap.get("price"));	
			}else
			{
			mViewHolder.getmTvPrice().setText("￥"+hashMap.get("price"));
			}
			mViewHolder.getmTvRoomType().setText(hashMap.get("roomtype")+hashMap.get("count")+"间");
			if(hashMap.get("roomState").equals("有房"))
			{
				mViewHolder.getmTvOperate().setText(hashMap.get("operate"));
				mViewHolder.getmTvOperate().setClickable(true);
			}else
			{
				mViewHolder.getmTvOperate().setBackgroundResource(R.drawable.button_gray);
				mViewHolder.getmTvOperate().setText(hashMap.get("operate"));
				mViewHolder.getmTvOperate().setClickable(false);
			}
			mViewHolder.getmTvDes().setText(hashMap.get("des"));
			downloadImage(mViewHolder.getmIvPhoto(), hashMap.get("pictrue"));
			

			final LinearLayout layDes = mViewHolder.getmLayDes();
			if (View.GONE != layDes.getVisibility()) {
				layDes.setVisibility(View.GONE);
			}
			mViewHolder.getmLayContent().setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							layDes.setVisibility(View.GONE == layDes
									.getVisibility() ? View.VISIBLE : View.GONE);
						}
					});
			mViewHolder.getmTvOperate().setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mCallBack.callBack(position);
				}
			});
		}
		return convertView;
	}
	
}
