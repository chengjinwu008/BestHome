package com.hhinns.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hhinns.adapter.HotelListActivityAdapter;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.model.OrderModel;
import com.hhinns.protocol.SEARCH;

public class HotelListActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private Intent mIntent = null;

	private ListView mListView = null;
	private HotelListActivityAdapter mAdapter = null;
	private List<Map<String, String>> mList = null;

	private RadioButton mBtnDefault = null;
	private RadioButton mBtnPrice = null;
	private RadioButton mBtnDistance = null;

	public static OrderModel dataModel = null;
	private View footer = null;
	private TextView mTvMore = null;

	private TextView mTvEnter;
	private TextView mTvOut;
	private TextView mTvCity;
	private TextView mTvCout;
	public SEARCH search;

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			intent = new Intent(HotelListActivity.this,
					HotelMapListActivity.class);
			intent.putExtra("title", mTvTitle.getText().toString());
			intent.putExtra("enter", mTvEnter.getText().toString());
			intent.putExtra("out", mTvOut.getText().toString());
			intent.putExtra("city", mTvCity.getText().toString());
			intent.putExtra("count", dataModel.hotelList.size() + "家");
			startActivity(intent);
			break;
		case R.id.tvMore:
			dataModel.searchHotelMore(OrderActivity.search);
			break;
		case R.id.btnDefault:
			mBtnDefault.setChecked(true);
			mBtnPrice.setChecked(false);
			mBtnDistance.setChecked(false);
			OrderActivity.search.setOrderby("default");
			dataModel.searchHotel(OrderActivity.search);
			break;
		case R.id.btnPrice:
			mBtnDefault.setChecked(false);
			mBtnPrice.setChecked(true);
			mBtnDistance.setChecked(false);
			OrderActivity.search.setOrderby("price");
			dataModel.searchHotel(OrderActivity.search);
			break;
		case R.id.btnDistance:
			mBtnDefault.setChecked(false);
			mBtnPrice.setChecked(false);
			mBtnDistance.setChecked(true);
			OrderActivity.search.setOrderby("distance");
			dataModel.searchHotel(OrderActivity.search);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_list);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mIntent = getIntent();
		if (null != mIntent) {
			String title = mIntent.getStringExtra("title");
			if (!TextUtils.isEmpty(title)) {
				mTvTitle.setText(title);
			}
		}

		search = OrderActivity.search;

		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.map);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);

		mBtnDefault = (RadioButton) findViewById(R.id.btnDefault);
		mBtnDefault.setChecked(true);
		mBtnDefault.setOnClickListener(this);
		mBtnPrice = (RadioButton) findViewById(R.id.btnPrice);
		mBtnPrice.setOnClickListener(this);
		mBtnDistance = (RadioButton) findViewById(R.id.btnDistance);
		mBtnDistance.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.listView);
		footer = getLayoutInflater().inflate(R.layout.layout_more, null);

		mTvEnter = (TextView) findViewById(R.id.enterDate);
		mTvOut = (TextView) findViewById(R.id.outDate);
		mTvCity = (TextView) findViewById(R.id.tvCity);
		mTvCout = (TextView) findViewById(R.id.tvCount);

		mTvMore = (TextView) footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
		mListView.addFooterView(footer);
		mList = new ArrayList<Map<String, String>>();
		dataModel = new OrderModel(this);
		dataModel.searchHotel(search);
		dataModel.addResponseListener(this);
		bindDataToListView();

		String indays[] = OrderActivity.search.indate.split("-");
		String outdays[] = OrderActivity.search.outdate.split("-");
		if (null != indays && indays.length > 2) {
			mTvEnter.setText("入住：" + indays[1] + "-" + indays[2]);
		}
		if (null != outdays && outdays.length > 2) {
			mTvOut.setText("离店：" + outdays[1] + "-" + outdays[2]);
		}
		mTvCity.setText(OrderActivity.search.city);
	}

	private void bindDataToListView() {
		mAdapter = new HotelListActivityAdapter(getApplicationContext(),
				dataModel.hotelList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = dataModel.hotelList.get(position);
				if (null != map && !map.isEmpty()) {
					Intent intent = new Intent(getApplicationContext(),
							RoomListActivity.class);
					intent.putExtra("hotelName", map.get("name"));
					intent.putExtra("id", map.get("id"));
					intent.putExtra("lat", map.get("lat"));
					intent.putExtra("lon", map.get("lon"));
					intent.putExtra("addr", map.get("addre"));
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if (jo == null) {
			mTvMore.setVisibility(View.GONE);
		}
		String responseStatus = jo.optString("status");
		if (null != responseStatus && responseStatus.equals("ok")) {
			if (dataModel.hotelList.size() % dataModel.pages != 0) {
				mTvMore.setVisibility(View.GONE);
			} else {
				mTvMore.setVisibility(View.VISIBLE);
			}

			mTvCout.setText(dataModel.hotelList.size() + "家");
			mAdapter.notifyDataSetChanged();
		}
	}
}
