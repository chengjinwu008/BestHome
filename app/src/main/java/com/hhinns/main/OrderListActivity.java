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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hhinns.adapter.OrderListActivityAdapter;
import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.OrderModel;
import com.hhinns.view.listview.XListView;
import com.hhinns.view.listview.XListView.IXListViewListener;

public class OrderListActivity extends BaseActivity 
implements OnClickListener,BusinessResponse,IXListViewListener{
	
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	private XListView mListView = null;
	private List<Map<String, String>> mList = null;
	private OrderListActivityAdapter mAdapter = null;
	private OrderModel dataModel = null;
	private View footer = null;
	private TextView mTvMore = null;
	private Button mBtnRight;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.tvMore:
			dataModel.orderListMore();
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorable_list);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.member_center_order);
		
		footer = getLayoutInflater().inflate(R.layout.layout_more, null);

		mTvMore = (TextView)footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
		mListView = (XListView) findViewById(R.id.listView);
		
		mListView.setPullLoadEnable(true);
		mListView.setRefreshTime();
		mListView.setXListViewListener(this, 1);
		
		mList = new ArrayList<Map<String, String>>();
		dataModel = new OrderModel(this);
		dataModel.orderList();
		dataModel.addResponseListener(this);
		bindDataToListView();
	}

	private void bindDataToListView() {
		mAdapter = new OrderListActivityAdapter(getApplicationContext(), dataModel.orderlList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				if(pos!=0)
				{
				Intent intent = new Intent(OrderListActivity.this,ReservationDetailActivity.class);
				intent.putExtra("id", dataModel.orderlList.get(pos-1).get("number"));
				intent.putExtra("ispay", dataModel.orderlList.get(pos-1).get("state"));
				startActivityForResult(intent, 0);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0){
			if(resultCode==12){
				dataModel.orderList();
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			if (dataModel.orderlList.size() % dataModel.pages != 0) {
				mListView.setPullLoadEnable(false);
			}else
			{
				mListView.setPullLoadEnable(true);
			}
			mAdapter.notifyDataSetChanged();
		}
		if(null==dataModel.orderlList||dataModel.orderlList.size()==0)
		{
			CommonUtils.showToast(getApplicationContext(), "暂无订单记录");
			mListView.setPullLoadEnable(false);
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		dataModel.orderList();
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		dataModel.orderListMore();
	}
}
