package com.hhinns.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.hhinns.library.CommonUtils;
import com.hhinns.library.DummyTabContent;
import com.hhinns.library.ResourceUtils;
import com.hhinns.main.ui.HotelIntroFragment;
import com.hhinns.main.ui.NearbyFacilityFragment;
import com.hhinns.model.ProccessForNearBy;
import com.hhinns.model.RoomModel;

public class RoomListActivity extends BaseActivity implements OnClickListener,
		OnTabChangeListener {
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;
	private Button mBtnRight = null;
	private Intent mIntent = null;

	private TabHost mTabHost = null;
	private String[] mDataArray = null;
	private LinearLayout mIndicator = null;

	private Fragment[] mFragments = null;
	public static RoomModel dataModel;
	public static String id;
	public static String address;
	 public static String lat;
	 public static String lon;
	private boolean isStart = true;
	private FragmentTransaction localFragmentTransaction = null;
	public static ProccessForNearBy dataModel_;
	public static boolean  isNear = false;
	public static String title;

	
	public void onTabChanged(String tabId) {
		int index = Integer.parseInt(tabId);
		FragmentManager fragmentManager = getSupportFragmentManager();
		localFragmentTransaction = getSupportFragmentManager().beginTransaction();

		if (isStart) {
			localFragmentTransaction.add(R.id.realtabcontent, mFragments[0]);
			localFragmentTransaction.add(R.id.realtabcontent, mFragments[1]);
			localFragmentTransaction.add(R.id.realtabcontent, mFragments[2]);
			localFragmentTransaction.add(R.id.realtabcontent, mFragments[3]);
			isStart = false;
		}
		localFragmentTransaction.addToBackStack(null);
		switch (index) {
		case 0:
			localFragmentTransaction.hide(mFragments[1]);
			localFragmentTransaction.hide(mFragments[2]);
			localFragmentTransaction.hide(mFragments[3]);
			localFragmentTransaction.show(mFragments[0]);
			break;
		case 1:
			localFragmentTransaction.hide(mFragments[0]);
			localFragmentTransaction.hide(mFragments[2]);
			localFragmentTransaction.hide(mFragments[3]);
			localFragmentTransaction.show(mFragments[1]);
			break;

		case 2:
			isNear = true;
			localFragmentTransaction.hide(mFragments[0]);
			localFragmentTransaction.hide(mFragments[1]);
			localFragmentTransaction.hide(mFragments[3]);
			localFragmentTransaction.show(mFragments[2]);
			((NearbyFacilityFragment)mFragments[2]).Viewchange();
			break;

		case 3:
			isNear = false;
			localFragmentTransaction.hide(mFragments[0]);
			localFragmentTransaction.hide(mFragments[1]);
			localFragmentTransaction.hide(mFragments[3]);
			localFragmentTransaction.show(mFragments[2]);
			((NearbyFacilityFragment)mFragments[2]).Viewchange();
			break;

		default:
			break;
		}
		localFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		localFragmentTransaction.commitAllowingStateLoss();
		// if (null != mFragments && null != mFragments[index]
		// && !mFragments[index].isAdded()) {
		// FragmentManager fragmentManager = getSupportFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.realtabcontent, mFragments[index])
		// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		// .commit();
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			if (CommonUtils.getSharedPreFerences(RoomListActivity.this,
					CommonUtils.SHARED_USER_NAME).equals("")) {
				CommonUtils.changeActivity(RoomListActivity.this,
						LoginActivity.class);
			} else {
				dataModel.collection(id);
			}
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_list);
		initComponents();
	}

	private void initComponents() {
		if (dataModel == null)
			dataModel = new RoomModel(this);
		if (null != getIntent())
			id = getIntent().getStringExtra("id");
		if (null != getIntent())
		{
			address = getIntent().getStringExtra("addr");
			lat = getIntent().getStringExtra("lat");
			lon = getIntent().getStringExtra("lon");
		}
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mIntent = getIntent();
		if (null != mIntent) {
			 title = mIntent.getStringExtra("hotelName");
			if (!TextUtils.isEmpty(title)) {
				mTvTitle.setText(title);
			}
			address = mIntent.getStringExtra("addr");
		}

		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.enshrine);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setVisibility(View.VISIBLE);
		mBtnRight.setOnClickListener(this);
		
		dataModel_ = new ProccessForNearBy(RoomListActivity.this);
		if(null!=lon&&null!=lat)
		dataModel_.nearNew(lon, lat);

		initTabHost();
		
//		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
//			
//			@Override
//			public void onTabChanged(String tabId) {
//				// TODO Auto-generated method stub
//				onTabChanged(tabId);
//			}
//		});
	}

	@SuppressWarnings("unchecked")
	private void initTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setup();

		mDataArray = getResources().getStringArray(R.array.hotelinfo_menu_item);
		mFragments = new Fragment[mDataArray.length];

		for (int i = 0; i < mFragments.length; i++) {
			Class<Fragment> classObj = (Class<Fragment>) CommonUtils
					.buildClass(getString(R.string.fragment_package),
							mDataArray[i].split("#")[3]);
			try {
				mFragments[Integer.parseInt(mDataArray[i].split("#")[2])] = classObj
						.newInstance();
			} catch (InstantiationException ex) {

			} catch (IllegalAccessException ex) {

			}
		}
		for (String menuItem : mDataArray) {
			mIndicator = (LinearLayout) getLayoutInflater().inflate(
					R.layout.layout_menu_item, null);

			TextView tabTextView = (TextView) mIndicator
					.findViewById(R.id.itemText);
			tabTextView.setText(menuItem.split("#")[0]);

			ImageView tabImageView = (ImageView) mIndicator
					.findViewById(R.id.itemImage);
			tabImageView.setBackgroundResource(ResourceUtils
					.getDrawableIdentifier(getApplicationContext(),
							menuItem.split("#")[1]));

			// Class<Fragment> classObj = (Class<Fragment>) CommonUtils
			// .buildClass(getString(R.string.fragment_package),
			// menuItem.split("#")[3]);
			// try {
			// mFragments[Integer.parseInt(menuItem.split("#")[2])] = classObj
			// .newInstance();
			// } catch (InstantiationException ex) {
			//
			// } catch (IllegalAccessException ex) {
			//
			// }
			mTabHost.addTab(mTabHost.newTabSpec(menuItem.split("#")[2])
					.setIndicator(mIndicator)
					.setContent(new DummyTabContent(getBaseContext())));

		}

		 mTabHost.setCurrentTab(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
}
