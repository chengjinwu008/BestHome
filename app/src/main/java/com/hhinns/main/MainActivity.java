package com.hhinns.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hhinns.adapter.MainNewActivityAdapter;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.DisplayUtils;
import com.hhinns.library.ResourceUtils;
import com.hhinns.model.ProccessForAd;
import com.hhinns.model.ProccessForPhone;
import com.hhinns.model.ProccessForTip;
import com.hhinns.protocol.MYLOCATION;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends BaseActivity implements OnClickListener ,BusinessResponse{
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

//	private ListView mGridView = null;
	public static List<Map<String, String>> mList = null;
//	private MainNewActivityAdapter mAdapter = null;
	public static ProccessForPhone proPhone = null;
	public static ProccessForTip proTip = null;
	
	private LocationClient mLocClient;
	public MyLocationListenner myListener = null;
	public static MYLOCATION mLocation = new MYLOCATION();
	private LinearLayout linPhone;
	private TextView tvPhone;
	
	public static ProccessForAd dataAd = null;

	private LinearLayout linViewOne;
	private LinearLayout linViewTwo;
	private LinearLayout linViewThree;
	
	private LinearLayout linViewFour;
	private LinearLayout linViewFive;
	private LinearLayout linViewSix;
	
	private LinearLayout linViewSeven;
	private LinearLayout linViewEght;
	private LinearLayout linViewNine;
	
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnRight:
			startActivity(new Intent(getApplicationContext(),
					LoginActivity.class));
			break;
		case R.id.linPhone:
			if(null!=proPhone.phoneNum)
			{
			CommonUtils.callPhone(MainActivity.this, proPhone.phoneNum);
			}else
			{
				CommonUtils.callPhone(MainActivity.this, "400555555");
			}
			break;
		case R.id.linViewOne:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(0).get("itemRoute"))); 
				intent.putExtra("title", mList.get(0).get("itemText"));
				startActivity(intent);
		break;
		case R.id.linViewTwo:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(1).get("itemRoute"))); 
				intent.putExtra("title", mList.get(1).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewThree:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(2).get("itemRoute"))); 
				intent.putExtra("title", mList.get(2).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewFour:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(3).get("itemRoute"))); 
				intent.putExtra("title", mList.get(3).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewFive:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(4).get("itemRoute"))); 
				intent.putExtra("title", mList.get(4).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewSix:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(5).get("itemRoute"))); 
				intent.putExtra("title", mList.get(5).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewSeven:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(6).get("itemRoute"))); 
				intent.putExtra("title", mList.get(6).get("itemText"));
				startActivity(intent);
			break;
		case R.id.linViewEght:
			intent = new Intent();  
		    intent.setAction(Intent.ACTION_PICK);  
		    intent.setData(ContactsContract.Contacts.CONTENT_URI);  
		    startActivityForResult(intent, 1);  
			break;
		case R.id.linViewNine:
			 intent = new Intent(MainActivity.this,
						CommonUtils.buildComponent(MainActivity.this,
								mList.get(8).get("itemRoute"))); 
				intent.putExtra("title", mList.get(8).get("itemText"));
				startActivity(intent);
			break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		initPosiation();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBtnLeft
				.getLayoutParams();
		layoutParams.width = DisplayUtils.dp2Px(this, 30);
		layoutParams.height = DisplayUtils.dp2Px(this, 30);
		layoutParams.setMargins(0, 0, DisplayUtils.dp2Px(this, 10), 0);
		mBtnLeft.setBackgroundResource(R.drawable.icon_aboutus_n);
		mBtnLeft.setVisibility(View.VISIBLE);

		
		mBtnRight = (Button) findViewById(R.id.btnRight);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.app_name);
		mTvTitle.setGravity(Gravity.LEFT + Gravity.CENTER_VERTICAL);
		proPhone = new ProccessForPhone(getApplicationContext());
		proPhone.getPhone();
		proPhone.addResponseListener(this);
		
		proTip = new ProccessForTip(getApplicationContext());
		proTip.getPhone();
		
		dataAd = new ProccessForAd(getApplicationContext());
		dataAd.getAdd();
		tvPhone = (TextView)findViewById(R.id.tvPhone);

//		mGridView = (ListView) findViewById(R.id.gridView);
		
		linViewOne = (LinearLayout)findViewById(R.id.linViewOne);
		linViewOne.setOnClickListener(this);
		linViewTwo = (LinearLayout)findViewById(R.id.linViewTwo);
		linViewTwo.setOnClickListener(this);
		linViewThree = (LinearLayout)findViewById(R.id.linViewThree);
		linViewThree.setOnClickListener(this);
		linViewFour = (LinearLayout)findViewById(R.id.linViewFour);
		linViewFour.setOnClickListener(this);
		linViewFive = (LinearLayout)findViewById(R.id.linViewFive);
		linViewFive.setOnClickListener(this);
		linViewSix = (LinearLayout)findViewById(R.id.linViewSix);
		linViewSix.setOnClickListener(this);
		linViewSeven = (LinearLayout)findViewById(R.id.linViewSeven);
		linViewSeven.setOnClickListener(this);
		linViewEght = (LinearLayout)findViewById(R.id.linViewEght);
		linViewEght.setOnClickListener(this);
		linViewNine= (LinearLayout)findViewById(R.id.linViewNine);
		linViewNine.setOnClickListener(this);
		
		
		mList = new ArrayList<Map<String, String>>();
		bindDataToGridView();
	}

	private void bindDataToGridView() {
		String[] dataArray = getResources().getStringArray(R.array.menu_item);

		Map<String, String> map = null;
		for (int i = 0; i < dataArray.length; i++) {
			String[] dataArr = dataArray[i].split("#");

			map = new HashMap<String, String>();
			map.put("itemText", dataArr[0]);
			map.put("itemImage",
					String.valueOf(ResourceUtils.getDrawableIdentifier(
							getApplicationContext(), dataArr[1])));
			map.put("itemRoute", dataArr[2]);
			mList.add(map);
		}

//		mAdapter = new MainNewActivityAdapter(MainActivity.this, mList);
//		mGridView.setAdapter(mAdapter);
		linPhone = (LinearLayout)findViewById(R.id.linPhone);
		linPhone.setOnClickListener(this);
	}
	
	/**
	 * 定位
	 */
	private void initPosiation() {
		myListener = new MyLocationListenner();
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setScanSpan(3000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 监听函数，修改新位置的时候.http://www.baidu.com/link?url=zAKtzY_Joi6yXB0sxVHYdYAsjs-IVyQr3xBJ8WSjqPGeVCea_RLYoUFiRO9I874WesqVbKxOpWLTncQZ0agEcK
	 */
	public class MyLocationListenner implements BDLocationListener {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			String address = null;
			if(address ==null)
			{
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
				if (location.hasAddr()) {

					address = location.getAddrStr();
					mLocation.setLatitude(String.valueOf(location.getLatitude()));
//					mLocation.setLatitude("34.2317910000");
					mLocation.setLongitude(String.valueOf(location.getLongitude()));
//					mLocation.setLongitude("108.9465950000");
					mLocation.setCity(String.valueOf(location.getCity()));
//					mLocation.setCity("西安");
					mLocation.setCityid(String.valueOf(location.getCityCode()));
				}
			}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
			mLocClient = null;
		}
	}
	

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
		MobclickAgent.onPause(this);
	}

	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
		if(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME).equals(""))
		{
		mBtnRight.setText(R.string.login);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);
		}else
		{
			mBtnRight.setText(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME));
			mBtnRight.setOnClickListener(this);
			mBtnRight.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			mBtnRight.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 switch (requestCode) {  
	       case 1:  
	           if (data == null) {  
	               return;  
	           }  
	           Uri result = data.getData();  
	           String contactId = result.getLastPathSegment();  
	           String phone = "";  
	           String[] projection = new String[] { Phone.CONTACT_ID,Phone.NUMBER};    
	           Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,       
	                projection, // select sentence      
	                Phone.CONTACT_ID + " = ?", // where sentence      
	                new String[] { contactId }, // where values      
	                Phone.CONTACT_ID); // order by    
	             
	           if (null!=cursor&&cursor.moveToFirst()) {  
	               phone = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));  
	               CommonUtils.sendMSG(MainActivity.this, phone, String.format("亲,发现好星之家酒店的客户端很好用,可以用手机预定酒店," +
	               		"安卓下载地址：%s 苹果下载地址%s", proPhone.andUrl,proPhone.ioUrl));
	           }else
	           {
	        	   CommonUtils.showToast(getApplicationContext(), "获取联系人失败");
	           }
	           break;  
	    }  
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(jo!=null)
		{
		if(url.endsWith("c=hoteltel&a=gethoteltel"))
		{
			String responseStatus = jo.optString("status");
			if(null!=responseStatus&&responseStatus.equals("ok"))
			{
				tvPhone.setText("免费电话:"+proPhone.phoneNum);
			}
		}
		}
	}
	
	
	
	
	
}
