package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.DateTimePickDialogUtil;
import com.hhinns.library.ProtocolConst;
import com.hhinns.model.CityModel;
import com.hhinns.protocol.SEARCH;

public class OrderActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {
	private Button mBtnLeft = null;
	// private Button mBtnRight = null;
	private TextView mTvTitle = null;
	private Intent mIntent = null;
	private LinearLayout mBtnOK = null;
	private Button mBtnMoreCity = null;
	private Button mBtnMoreArea = null;
	private TextView mTvCity = null;
	private TextView mTvArea = null;
	private String cityId;

	private TextView enterDay;
	private TextView enterMon;
	private TextView enterWeek;

	private TextView outDay;
	private TextView outMon;
	private TextView outWeek;

	private RelativeLayout rlEnterDate;
	private RelativeLayout rlOutDate;

	public static CityModel dataModel = null;
	public static SEARCH search;
	private String cityName = "西安";

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnOK:
			if (null != search.getShopareaid()) {
				intent = new Intent(getApplicationContext(),
						HotelListActivity.class);
				intent.putExtra("title", mTvTitle.getText().toString());
				startActivity(intent);
			} else {
				search.setShopareaid("");
				intent = new Intent(getApplicationContext(),
						HotelListActivity.class);
				intent.putExtra("title", mTvTitle.getText().toString());
				startActivity(intent);
			}
			break;
		case R.id.btnSelectCity:
			intent = new Intent(OrderActivity.this, CityListActivity.class);
			intent.putExtra("type", "0");
			startActivityForResult(intent, 0);
			break;
		case R.id.btnSelectShopArea:
			intent = new Intent(OrderActivity.this, CityListActivity.class);
			intent.putExtra("type", "1");
			intent.putExtra("id", cityId);
			startActivityForResult(intent, 1);
			break;
		case R.id.rlEnterDate:
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
					OrderActivity.this, "选择入住时间");
			dateTimePicKDialog.dateTimePicKDialog(enterDay, enterMon,
					enterWeek, false);
			break;
		case R.id.rlOutDate:
			DateTimePickDialogUtil dateTimePicKDialog1 = new DateTimePickDialogUtil(
					OrderActivity.this, "选择离店时间");
			dateTimePicKDialog1.dateTimePicKDialog(outDay, outMon, outWeek,
					true);
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (null != arg2 && arg1 == 0) {
			if (arg2.getStringExtra("id") != null
					&& arg2.getStringExtra("name") != null) {
				cityName = arg2.getStringExtra("name");
				mTvCity.setText("城市:" + cityName);
				cityId = arg2.getStringExtra("id");
				search.setCityid(cityId);
				search.setShopareaid(null);
				search.setCity(cityName);
				mTvArea.setText("商圈:");
			}
		} else if (null != arg2 && arg1 == 1) {
			if (arg2.getStringExtra("id") != null
					&& arg2.getStringExtra("name") != null) {
				mTvArea.setText("商圈:" + arg2.getStringExtra("name"));
				// cityId = arg2.getStringExtra("id");
				search.setShopareaid(arg2.getStringExtra("id"));
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initComponents();
		dataModel = new CityModel(getApplicationContext());
		dataModel.addResponseListener(this);
		dataModel.fetchCity();
		// initGallery();
	}

	private void initComponents() {
		search = new SEARCH();
		search.setCity(cityName);
		search.setLatitude(MainActivity.mLocation.latitude);
		search.setLongitude(MainActivity.mLocation.longitude);
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		// mTvTitle.setPadding(
		// getResources().getDimensionPixelOffset(
		// R.dimen.titleBarPaddingLeft), 0, 0, 0);
		mIntent = getIntent();
		if (null != mIntent) {
			String title = mIntent.getStringExtra("title");
			if (!TextUtils.isEmpty(title)) {
				mTvTitle.setText(title);
			}
		}

		mTvCity = (TextView) findViewById(R.id.tvCurCity);
		mTvArea = (TextView) findViewById(R.id.tvCurShopArea);

		// mBtnRight = (Button) findViewById(R.id.btnRight);
		// LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
		// mBtnRight
		// .getLayoutParams();
		// layoutParams.width = LayoutParams.WRAP_CONTENT;
		// if(null!=MainActivity.mLocation.getCity())
		// mBtnRight.setText("当前:"+MainActivity.mLocation.getCity());
		// mBtnRight.setBackgroundColor(getResources().getColor(
		// android.R.color.transparent));
		// mBtnRight.setVisibility(View.GONE);

		mBtnMoreCity = (Button) findViewById(R.id.btnSelectCity);
		mBtnMoreCity.setOnClickListener(this);

		mBtnMoreArea = (Button) findViewById(R.id.btnSelectShopArea);
		mBtnMoreArea.setOnClickListener(this);
		enterDay = (TextView) findViewById(R.id.tvEnterDay);
		enterMon = (TextView) findViewById(R.id.tvEnterMonth);
		enterWeek = (TextView) findViewById(R.id.tvEnterWeek);
		outDay = (TextView) findViewById(R.id.tvOutDay);
		outMon = (TextView) findViewById(R.id.tvOutMonth);
		outWeek = (TextView) findViewById(R.id.tvOutWeek);

		rlEnterDate = (RelativeLayout) findViewById(R.id.rlEnterDate);
		rlEnterDate.setOnClickListener(this);
		rlOutDate = (RelativeLayout) findViewById(R.id.rlOutDate);
		rlOutDate.setOnClickListener(this);

		String todays[] = CommonUtils.getDate().split("-");
		String nextday[] = CommonUtils.getInternalDateByDay(1).split("-");

		if (null != todays && todays.length > 3) {
			enterWeek.setText(todays[todays.length - 1]);
			enterDay.setText(todays[todays.length - 2]);
			enterMon.setText(todays[todays.length - 3]);
			search.setIndate(todays[0] + "-" + todays[1] + "-" + todays[2]);
		}
		if (null != nextday && nextday.length > 3) {
			outWeek.setText(nextday[nextday.length - 1]);
			outDay.setText(nextday[nextday.length - 2]);
			outMon.setText(nextday[nextday.length - 3]);
			search.setOutdate(nextday[0] + "-" + nextday[1] + "-" + nextday[2]);
		}
		search.setOrderby("default");
		search.setCityid("288");
		cityId = "288";
		// search.setShopareaid("2511");

		mBtnOK = (LinearLayout) findViewById(R.id.btnOK);
		mBtnOK.setOnClickListener(this);
	}

	// private void initGallery()
	// {
	// images_ga = (GuideGallery) findViewById(R.id.ggAd);
	// images_ga.setImageActivity(this);
	// ImageAdapter imageAdapter = new ImageAdapter(this);
	// images_ga.setAdapter(imageAdapter);
	// timeTaks = new ImageTimerTask();
	// autoGallery.scheduleAtFixedRate(timeTaks, 2000, 5000);
	// // images_ga.setOnItemClickListener(new OnItemClickListener() {
	// //
	// // @Override
	// // public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// // long arg3) {
	// // // TODO Auto-generated method stub
	// // CommonUtils.changeActivity(FavorableListActivity.this,
	// FavorableWebDetail.class, "id",
	// //
	// String.format("%s?c=favorable_activities&a=get_favorable_activity_info&id=%s",
	// CommonUtils.NEW_BASE_URL,
	// // dataModel.favorableList.get(position).get("id")));
	// // }
	// // });
	// timeThread = new Thread() {
	// public void run() {
	// while (!isExit) {
	// try {
	// Thread.sleep(1500);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// synchronized (timeTaks) {
	// if (!timeFlag) {
	// timeTaks.timeCondition = true;
	// timeTaks.notifyAll();
	// }
	// }
	// timeFlag = true;
	// }
	// };
	// };
	// timeThread.start();
	// }

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if (null != responseStatus && responseStatus.equals("ok")) {
			if (url.endsWith(ProtocolConst.FETCH_CITY)) {
				mTvCity.setText("城市：" + dataModel.cityList.get(0).get("name"));
				cityId = dataModel.cityList.get(0).get("id");
			}
		}
	}

	//
	// final Handler autoGalleryHandler = new Handler() {
	// public void handleMessage(Message message) {
	// super.handleMessage(message);
	// switch (message.what) {
	// case 1:
	// images_ga.setSelection(message.getData().getInt("pos"));
	// break;
	// }
	// }
	// };

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// timeFlag = false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// timeTaks.timeCondition = false;
	}

	// public class ImageTimerTask extends TimerTask {
	// public volatile boolean timeCondition = true;
	//
	// public void run() {
	// synchronized (this) {
	// while (!timeCondition) {
	// try {
	// Thread.sleep(100);
	// wait();
	// } catch (InterruptedException e) {
	// Thread.interrupted();
	// }
	// }
	// }
	// try {
	// gallerypisition = images_ga.getSelectedItemPosition() + 1;
	// System.out.println(gallerypisition + "");
	// Message msg = new Message();
	// Bundle date = new Bundle();// 存放数据
	// date.putInt("pos", gallerypisition);
	// msg.setData(date);
	// msg.what = 1;// 消息标识
	// autoGalleryHandler.sendMessage(msg);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	// private Timer autoGallery = new Timer();
	// public GuideGallery images_ga;
	// private Thread timeThread = null;
	// public boolean timeFlag = true;
	// private boolean isExit = false;
	// public ImageTimerTask timeTaks = null;
	// public int gallerypisition = 0;

}
