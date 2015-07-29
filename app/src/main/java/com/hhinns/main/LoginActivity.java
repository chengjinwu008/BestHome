package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.model.CustomerModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener,BusinessResponse {
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private EditText mEtUsername = null;
	private EditText mEtPassword = null;
	private Button mBtnConfirm = null;
	private CustomerModel dataModel = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnRight:
			startActivity(new Intent(getApplicationContext(),
					RegistActivity.class));
			break;
		case R.id.btnConfirm:
			if(mEtUsername.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "账号不能为空");
				return;
			}
			if(mEtPassword.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "密码不能为空");
				return;
			}
			dataModel.Login(mEtUsername.getText().toString().trim(), 
					mEtPassword.getText().toString().trim());
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initComponents();
		dataModel = new CustomerModel(getApplicationContext());
		dataModel.addResponseListener(this);
	}

	private void initComponents() {
		
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);
		
		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.regist);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.login);

		mEtUsername = (EditText) findViewById(R.id.etUsername);
		mEtPassword = (EditText) findViewById(R.id.etPassword);
		mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
		mBtnConfirm.setOnClickListener(this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			CommonUtils.showToast(getApplicationContext(), "登录成功");
			finish();
		}
	}
	
	
}
