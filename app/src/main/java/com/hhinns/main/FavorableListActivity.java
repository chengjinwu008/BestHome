package com.hhinns.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.FavorableModel;
import com.hhinns.model.ProccessForFavorable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FavorableListActivity extends BaseActivity implements
		OnClickListener ,BusinessResponse{
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	private ListView mListView = null;
	private List<Map<String, String>> mList = null;
	private SimpleAdapter mAdapter = null;

	private Intent mIntent = null;
	private ProccessForFavorable dataModel;
	private View footer = null;
	private TextView mTvMore = null; 

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.tvMore:
			dataModel.favorableNew(false);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorable_list_1);
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

		mListView = (ListView) findViewById(R.id.listView);
		mList = new ArrayList<Map<String, String>>();
		footer = getLayoutInflater().inflate(R.layout.layout_more, null);
		mTvMore = (TextView)footer.findViewById(R.id.tvMore);
		mTvMore.setOnClickListener(this);
		mListView.addFooterView(footer);
		
		dataModel = new ProccessForFavorable(this);
		dataModel.addResponseListener(this);
		dataModel.favorableNew(true);
		bindDataToListView();
	}

	private void bindDataToListView() {
		mAdapter = new SimpleAdapter(getApplicationContext(), dataModel.favorableList,
				R.layout.activity_favorable_list_item, new String[] { "name",
						"time" }, new int[] { R.id.tvTitle, R.id.tvPubdate });
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonUtils.changeActivity(FavorableListActivity.this, FavorableWebDetail.class, "id", 
						String.format("%s?c=favorable_activities&a=get_favorable_activity_info&id=%s", CommonUtils.NEW_BASE_URL,
								dataModel.favorableList.get(position).get("id")));
			}
		});
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(jo==null)
		{
			mTvMore.setVisibility(View.GONE);
		}
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			if (dataModel.favorableList.size() % dataModel.pages != 0) {
				mTvMore.setVisibility(View.GONE);
			}else
			{
				mTvMore.setVisibility(View.VISIBLE);
			}
			mAdapter.notifyDataSetChanged();
		}
		if(null==dataModel.favorableList||dataModel.favorableList.size()==0)
		{
			CommonUtils.showToast(getApplicationContext(), "暂无优惠活动");
		}
	}
}
