package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.CustomerModel;
import com.hhinns.model.ProccessForNearBy;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedbackActivity extends BaseActivity implements OnClickListener ,BusinessResponse{
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;	
	
	private EditText mEtName = null;
	private EditText mEtMobile = null;
	private EditText mEtEmail = null;
	private EditText mEtContent = null;
	private Button mBtnSubmit = null;
	
	private ProccessForNearBy dataModel;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnSubmit:
			if(mEtName.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "姓名不能为空");
				return;
			}
			if(mEtMobile.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "手机号不能为空");
				return;
			}else if(!CommonUtils.matcherPhone(mEtMobile.getText().toString().trim()))
			{
				CommonUtils.showToast(getApplicationContext(), "手机号不正确");
				return;
			}
			if(mEtEmail.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "邮箱号不能为空");
				return;
			}else if(!CommonUtils.isEmail(mEtEmail.getText().toString().trim()))
			{
				CommonUtils.showToast(getApplicationContext(), "请填写正确的邮箱号");
				return;
			}
			if(mEtContent.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "反馈信息不能为空");
				return;
			}
			dataModel.feedBack(mEtName.getText().toString().trim(), 
					mEtMobile.getText().toString().trim(),
					mEtEmail.getText().toString().trim(), 
					mEtContent.getText().toString().trim());
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText("意见反馈");
		
		mEtName = (EditText)findViewById(R.id.etName);
		mEtMobile = (EditText)findViewById(R.id.etMobile);
		mEtEmail = (EditText)findViewById(R.id.etEmail);
		mEtContent = (EditText)findViewById(R.id.etContent);
		
		mBtnSubmit = (Button)findViewById(R.id.btnSubmit);
		mBtnSubmit.setOnClickListener(this);
		
		dataModel = new ProccessForNearBy(this);
		dataModel.addResponseListener(this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");

		if (null!=responseStatus&&responseStatus.equals("ok")) {
			finish();
		}else
		{
			CommonUtils.showToast(FeedbackActivity.this, "提交意见反馈失败");
		}
	}
}
