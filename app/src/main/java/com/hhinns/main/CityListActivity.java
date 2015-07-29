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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.model.CityModel;

public class CityListActivity extends BaseActivity implements OnClickListener
,BusinessResponse{
	
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	private ListView mListView = null;
	private List<Map<String, String>> mList = null;
	private SimpleAdapter mAdapter = null;

	private CityModel dataModel = null;
	private String type;
	private String id ;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_list);
		if(null!=getIntent())
		type = getIntent().getStringExtra("type");
		id =  getIntent().getStringExtra("id");
		dataModel = new CityModel(getApplicationContext());
		dataModel.addResponseListener(this);
		if(null!=type&&type.equals("0"))
		{
		dataModel.fetchCity();
		}else if(null!=type&&type.equals("1")&&null!=id)
		{
		dataModel.fetchShopArea(id);	
		}
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.citylist_title);

		mListView = (ListView) findViewById(R.id.lvCitys);
		mList = new ArrayList<Map<String, String>>();
		bindDataToListView();
//		getData();
	}

	private void bindDataToListView() {
		if(null!=type&&type.equals("0"))
		{
			mAdapter = new SimpleAdapter(getApplicationContext(), dataModel.cityList,
					R.layout.activity_city_list_item, new String[]{"name"},
					new int []{R.id.tvName});
		}else if(null!=type&&type.equals("1")&&null!=id)
		{
			mAdapter = new SimpleAdapter(getApplicationContext(), dataModel.areaList,
					R.layout.activity_city_list_item, new String[]{"name"},
					new int []{R.id.tvName});
		}
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Map<String, String> map = null;
				if(null!=type&&type.equals("0"))
				{
				map = dataModel.cityList.get(pos);
				}else if(null!=type&&type.equals("1")&&null!=id)
				{
				map = dataModel.areaList.get(pos);	
				}
				if(map!=null)
				{
				Intent intent = new Intent();
				intent.putExtra("id",map.get("id"));
				intent.putExtra("name",map.get("name"));
				if(null!=type&&type.equals("0"))
				{
					setResult(0,intent);
				}else if(null!=type&&type.equals("1"))
				{
					setResult(1,intent);
				}
				finish();
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
//			if(null!=type&&type.equals("0"))
//			{
//				for(int i=0;i<dataModel.cityList.size();i++)
//				{
//					Map<String, String> map = dataModel.cityList.get(i);
//					mList.add(map);
//				}
//			}else if(null!=type&&type.equals("1")&&null!=id)
//			{
//				for(int i=0;i<dataModel.areaList.size();i++)
//				{
//					Map<String, String> map = dataModel.areaList.get(i);
//					mList.add(map);
//				}
//			}
			mAdapter.notifyDataSetChanged();
		}
	}
}
