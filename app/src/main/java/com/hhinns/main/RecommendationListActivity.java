package com.hhinns.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hhinns.adapter.HotelListActivityAdapter;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.RecommendMerchantModel;
import com.hhinns.view.listview.XListView;
import com.hhinns.view.listview.XListView.IXListViewListener;

public class RecommendationListActivity extends BaseActivity
implements OnClickListener,BusinessResponse ,IXListViewListener{
	private Button mBtnLeft = null;
	private Button  mBtnRight = null;
	private TextView mTvTitle = null;
	private XListView mListView = null;
	private HotelListActivityAdapter mAdapter = null;
	public static RecommendMerchantModel dataModel;
	private View footer = null;
	private TextView mTvMore = null; 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			Intent intent = new Intent(RecommendationListActivity.this,RecommendationListMapActivity.class);
			intent.putExtra("title", mTvTitle.getText().toString());
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_collect_list);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.recommendation_title);

		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.map);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);

		mListView = (XListView) findViewById(R.id.listView);
		mListView.setPullLoadEnable(true);
		mListView.setRefreshTime();
		mListView.setXListViewListener(this, 1);
		footer = getLayoutInflater().inflate(R.layout.layout_more, null);
		mTvMore = (TextView)footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
//		mListView.addFooterView(footer);
		dataModel = new RecommendMerchantModel(this);
		dataModel.addResponseListener(this);
		dataModel.searchHotel();
		bindDataToListView();
//		getData();
	}

	private void bindDataToListView() {
		mAdapter = new HotelListActivityAdapter(getApplicationContext(), dataModel.hotelList);
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
					intent.putExtra("hotelName", map.get("name"));
					intent.putExtra("id", map.get("id"));
					intent.putExtra("lat", map.get("lat"));
					intent.putExtra("lon", map.get("lon"));
					intent.putExtra("addr", map.get("addre"));
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
			mAdapter.notifyDataSetChanged();
		}
		if(null==dataModel.hotelList||dataModel.hotelList.size()==0)
		{
			CommonUtils.showToast(getApplicationContext(), "暂无精品推荐");
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotel();
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotelMore();
	}
}
