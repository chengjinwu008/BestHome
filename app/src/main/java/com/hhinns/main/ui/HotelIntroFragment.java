package com.hhinns.main.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.hhinns.adapter.Bee_PageAdapter;
import com.hhinns.adapter.GalleryAdapter;
import com.hhinns.bitmap.BitmapDisplayConfig;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.BitmapUtils;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.main.R;
import com.hhinns.main.RoomListActivity;

public class HotelIntroFragment extends BaseFragment implements
		BusinessResponse {
	private ListView mListView = null;
	private SimpleAdapter mAdapter = null;
	private List<Map<String, String>> mList = null;
	private String id;

	private View listFooter;
	private Gallery gallery;
	private GalleryAdapter adapter;
	private View galleryPop;
	private BitmapUtils bitmap = null;
	private BitmapDisplayConfig con;
	private ArrayList<View> photoListView;
	private Bee_PageAdapter photoPageAdapter;
	private ViewPager vpGallery;
	private PopupWindow pop;
	private Activity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		if (null != mActivity.getIntent())
			id = mActivity.getIntent().getStringExtra("id");
		mListView = (ListView) inflater.inflate(R.layout.fragment_hotel_intro,
				container, false);
		listFooter = LayoutInflater.from(mActivity).inflate(
				R.layout.hotel_gallery_footer, null);

		gallery = (Gallery) listFooter.findViewById(R.id.galleryImg);
		con = new BitmapDisplayConfig();
		con.setLoadingDrawable(getResources().getDrawable(
				R.drawable.ic_launcher));

		bitmap = new BitmapUtils(mActivity.getApplicationContext());
		mListView.addHeaderView(listFooter);
		mList = new ArrayList<Map<String, String>>();
		RoomListActivity.dataModel.hotelDetail(RoomListActivity.id);
		RoomListActivity.dataModel.hotelGallery(RoomListActivity.id);
		RoomListActivity.dataModel.addResponseListener(this);
		photoListView = new ArrayList<View>();
		photoPageAdapter = new Bee_PageAdapter(photoListView);
		bindDataToListView();
		return mListView;
	}

	private void bindDataToListView() {
		mAdapter = new SimpleAdapter(mActivity.getApplicationContext(),
				RoomListActivity.dataModel.serviceList,
				R.layout.fragment_hotel_intro_item, new String[] { "tip",
						"content" }, new int[] { R.id.tvTip, R.id.tvContent });
		mListView.setFooterDividersEnabled(false);
		mListView.setAdapter(mAdapter);
	}

	
	
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if (null != responseStatus && responseStatus.equals("ok")) {
			if (url.endsWith(ProtocolConst.HOTEL_INFO)) {
				mAdapter.notifyDataSetChanged();
			} else {
				initgalleryData();
			}
		}
	}
	
	

	private void initgalleryData() {

		int galleryWidth = CommonUtils.getScreenWidth(mActivity);
		int itemWidth = 120;
		int spacing = 6;
		int offset;
		if (galleryWidth <= itemWidth) {
			offset = galleryWidth / 2 - itemWidth / 2 - spacing;
		} else {
			offset = galleryWidth - itemWidth - 2 * spacing;
		}
		MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
		mlp.setMargins(-offset+70, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);

		adapter = new GalleryAdapter(mActivity,
				RoomListActivity.dataModel.imageList, 120, 120);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				initPictrue();
				addBannerView(pos);
				pop.showAtLocation(
						getView(),
						Gravity.CENTER, 0, 0);
			}
		});
		gallery.setSelection(0);
		adapter.notifyDataSetChanged();

	}

	private void initPictrue() {
		galleryPop = LayoutInflater.from(mActivity).inflate(
				R.layout.pop_hotel_galler, null);
		vpGallery = (ViewPager)galleryPop.findViewById(R.id.vpGallery);
		vpGallery.setAdapter(photoPageAdapter);
		pop = new PopupWindow(galleryPop, LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT, true);
		galleryPop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
			}
		});
		pop.setBackgroundDrawable(new BitmapDrawable());
	}

	public void addBannerView(int pos) {

		if (RoomListActivity.dataModel.imageList.size() > 0) {
			photoListView.clear();
			for (int i = 0; i < RoomListActivity.dataModel.imageList.size(); i++) {
				String url =  RoomListActivity.dataModel.imageList.get(i);
				ImageView viewOne = (ImageView) LayoutInflater.from(mActivity)
						.inflate(R.layout.gallery_image, null);
				bitmap.display(viewOne,url, con);
				photoListView.add(viewOne);

			}
			photoPageAdapter.notifyDataSetChanged();
			vpGallery.setCurrentItem(pos);
		}
		
	}
}
