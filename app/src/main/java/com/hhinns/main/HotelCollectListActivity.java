package com.hhinns.main;

import java.util.ArrayList;
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
import android.widget.TextView;

import com.hhinns.adapter.CollectListActivityAdapter;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.library.RoomCallBack;
import com.hhinns.model.CollecttionModel;
import com.hhinns.view.listview.XListView;
import com.hhinns.view.listview.XListView.IXListViewListener;

public class HotelCollectListActivity extends BaseActivity 
implements OnClickListener ,BusinessResponse,IXListViewListener{
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private Intent mIntent = null;

	private XListView mListView = null;
	private CollectListActivityAdapter mAdapter = null;
	private List<Map<String, String>> mList = null;

	private CollecttionModel  dataModel;
	private View footer = null;
	private TextView mTvMore = null; 
	public static boolean isDelet= false;
	public static int pos  = 0;
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			if(mBtnRight.getText().toString().equals("编辑"))
			{
			mBtnRight.setText(R.string.collection_complete);
			isDelet= true;
			mAdapter.notifyDataSetChanged();
			}else
			{
				mBtnRight.setText(R.string.collection_delet);
				isDelet= false;
				mAdapter.notifyDataSetChanged();
			}
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
		
		
		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.collection_delet);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.member_center_collection);


		footer = getLayoutInflater().inflate(R.layout.layout_more, null);
		

		mListView = (XListView) findViewById(R.id.listView);
		mListView.setPullLoadEnable(true);
		mListView.setRefreshTime();
		mListView.setXListViewListener(this, 1);
		mTvMore = (TextView)footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
		mList = new ArrayList<Map<String, String>>();
		dataModel = new CollecttionModel(this);
		dataModel.addResponseListener(this);
		dataModel.searchHotel();
		bindDataToListView();
	}

	private void bindDataToListView() {
		mAdapter = new CollectListActivityAdapter(getApplicationContext(),dataModel.hotelList,new RoomCallBack() {
			
			@Override
			public void callBack(int pos) {
				// TODO Auto-generated method stub
				 HotelCollectListActivity.pos= pos;
				dataModel.deletCollection(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME),
						dataModel.hotelList.get(pos).get("id"),String.valueOf(pos) );
			}
		});
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
					intent.putExtra("name", map.get("hotelName"));
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
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			if(url.contains(ProtocolConst.COLLECTION_LIST))
			{
			if (dataModel.hotelList.size() % dataModel.pages != 0) {
				mListView.setPullLoadEnable(false);
			}else
			{
				mListView.setPullLoadEnable(true);
			}
			mAdapter.notifyDataSetChanged();
			}else
			{
				dataModel.hotelList.remove(pos);
				mAdapter.notifyDataSetChanged();
			}
		}else
		{
			mListView.setPullLoadEnable(false);
		}
		
		if(null==dataModel.hotelList||dataModel.hotelList.size()==0)
		{
			mBtnRight.setVisibility(View.GONE);
			mListView.setPullLoadEnable(false);
			CommonUtils.showToast(getApplicationContext(), "暂无收藏");
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotel();
	}

	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isDelet = false;
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		dataModel.searchHotelMore();
	}
}
