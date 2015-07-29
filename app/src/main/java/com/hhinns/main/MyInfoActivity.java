package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.CustomerModel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyInfoActivity extends BaseActivity implements OnClickListener,BusinessResponse{

	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;
	
	private TextView tvName = null;
	private TextView tvPhone= null;
	private TextView tvAddress = null;
	private TextView tvEmall = null;
	private TextView tvSex = null;
	
	public static CustomerModel dataModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		initCompontents();
	}
	
	private void initCompontents()
	{
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mTvTitle.setText(R.string.member_center_info);
		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.edit);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);
		
		tvName  = (TextView)findViewById(R.id.tvName);
		tvPhone= (TextView)findViewById(R.id.tvPhone);
		tvAddress = (TextView)findViewById(R.id.tvAddress);
		tvEmall = (TextView)findViewById(R.id.tvEmall);
		tvSex = (TextView)findViewById(R.id.tvSex);
		
		dataModel = new CustomerModel(this);
		dataModel.addResponseListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
	case R.id.btnRight:
			CommonUtils.changeActivity(MyInfoActivity.this, MyInfoEditActivity.class);
			break;

		default:
			break;
		}
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dataModel.CustomerInfo();
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");

		if (responseStatus.equals("ok")) {
			tvName.setText("您的姓名："+dataModel.customer.get("name"));
			tvPhone.setText("您的手机："+dataModel.customer.get("phone"));
			tvAddress.setText("身份证号："+dataModel.customer.get("idcard"));
			tvEmall.setText("您的邮箱："+dataModel.customer.get("email"));
			tvSex.setText("您的性别："+dataModel.customer.get("sex"));

		}
		
	}

}
