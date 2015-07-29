package com.hhinns.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TooManyListenersException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.navisdk.ui.widget.NewerGuideDialog;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.SpecialPriceModel;
import com.hhinns.view.listview.XListView;
import com.hhinns.view.listview.XListView.IXListViewListener;

public class FavorableInfoActivity extends BaseActivity implements
		OnClickListener ,BusinessResponse,IXListViewListener{
	private Button mBtnLeft = null;
	private Button  mBtnRight = null;
	private TextView mTvTitle = null;
	private SimpleAdapter mAdapter = null;
	private ArrayList<Map<String, String>> mList;
	private XListView mListView;
	public static SpecialPriceModel dataModel;
	private View footer = null;
	private TextView mTvMore = null; 
	
	private TextView tvDate;
//	private TextView tvCity;
	private TextView tvNumber;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.tvMore:
			dataModel.searchHotelMore(CommonUtils.getStringByCalender(Calendar.getInstance()));
			break;
		case R.id.btnRight:
			Intent intent = new Intent(FavorableInfoActivity.this,FavorableInfoMapActivity.class);
			intent.putExtra("title", mTvTitle.getText().toString());
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorable_info);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.favorable_info_special);
		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.map);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);
		
		mListView = (XListView)findViewById(R.id.listView);
		mListView.setPullLoadEnable(true);
		mListView.setRefreshTime();
		mListView.setXListViewListener(this, 1);
		footer = getLayoutInflater().inflate(R.layout.layout_more, null);
		mTvMore = (TextView)footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
		mList = new ArrayList<Map<String,String>>();
		tvDate = (TextView)findViewById(R.id.tvDate);
//		tvCity = (TextView)findViewById(R.id.tvCity);
		tvNumber = (TextView)findViewById(R.id.tvNumber);
		

		dataModel = new SpecialPriceModel(this);
		dataModel.addResponseListener(this);
		dataModel.searchHotel(CommonUtils.getStringByCalender(Calendar.getInstance()));
		tvDate.setText(CommonUtils.getStringByCalender(Calendar.getInstance()));
//		tvCity.setText(MainActivity.mLocation.getCity());
		bindDataToListView();
		
		
	}
	
	private void bindDataToListView() {
		mAdapter = new SimpleAdapter(getApplicationContext(), dataModel.hotelList,
				R.layout.activity_favorable_info_item, new String[] { "hotelName",
			"info","price" }, new int[] { R.id.tvTitle, R.id.tvInfo,R.id.tvPrice });
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0)
				{
				Map<String, String> map = dataModel.hotelList.get(position-1);
				if (null != map && !map.isEmpty()) {
					Intent intent = new Intent(getApplicationContext(),
							RoomListActivity.class);
					intent.putExtra("hotelName", map.get("hotelName"));
					intent.putExtra("id", map.get("id"));
					intent.putExtra("lat", map.get("lat"));
					intent.putExtra("lon", map.get("lon"));
					intent.putExtra("addr", map.get("addr"));
					startActivity(intent);
				}
				}
			}
		});
	}


	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(jo==null)
		{
			mListView.setPullLoadEnable(false);
			return;
		}
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			if (dataModel.hotelList.size() % dataModel.pages != 0) {
				mListView.setPullLoadEnable(false);
			}else
			{
				mListView.setPullLoadEnable(true);
			}
			tvNumber.setText(dataModel.hotelList.get(0).get("count")+"间特价房");
			mAdapter.notifyDataSetChanged();
		}
		if(null==dataModel.hotelList||dataModel.hotelList.size()==0)
		{
			CommonUtils.showToast(getApplicationContext(), "暂无特价信息");
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotel(CommonUtils.getStringByCalender(Calendar.getInstance()));
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotelMore(CommonUtils.getStringByCalender(Calendar.getInstance()));
	}
}
