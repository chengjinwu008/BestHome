package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.model.CustomerModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RegistActivity extends BaseActivity implements OnClickListener ,BusinessResponse {
	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private EditText mEtMobile = null;
	private EditText mEtName = null;
	private EditText mEtPass = null;
	private EditText mEtPassRep = null;
	private EditText mEtCode = null;

	private Button mBtnSubmit = null;
	private CustomerModel dataModel;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btnSubmit:
			if(mEtName.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "用户名不能为空");
				return;
			}
			if(mEtPass.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "密码不能为空");
				return;
			}
			if(mEtPassRep.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "请再次你的密码");
				return;
			}
			if(mEtMobile.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "用户手机号不能为空");
				return;
			}else if(!CommonUtils.matcherPhone(mEtMobile.getText().toString().trim()))
			{
				return;
			}
			if(mEtCode.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "验证码不能为空");
				return;
			}
			if(!mEtPassRep.getText().toString().trim().equals(mEtPass.getText().toString().trim()))
			{
				CommonUtils.showToast(getApplicationContext(), "两次输入的密码不同");
				return;
			}
			dataModel.Register(mEtName.getText().toString().trim(),
					mEtPass.getText().toString().trim(), 
					mEtMobile.getText().toString().trim(),mEtCode.getText().toString().trim());
			break;
		case R.id.btnRight:
			if(mEtMobile.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "获得验证码时手机号不能为空");
				return;
			}else 
				if(!CommonUtils.matcherPhone(mEtMobile.getText().toString().trim()))
			{
				CommonUtils.showToast(getApplicationContext(), "请输入正确的手机号");
				return;
			}
			dataModel.getAuthcode(mEtMobile.getText().toString().trim());
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
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
		mBtnRight.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mBtnRight.setPadding(10, 6, 6, 10);
		mBtnRight.setText(R.string.regist_get_authcode);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);
		mBtnRight.setVisibility(View.VISIBLE);
		
		

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.regist);

		mEtMobile = (EditText) findViewById(R.id.etMobile);
		mEtName = (EditText) findViewById(R.id.etName);
		mEtPass = (EditText) findViewById(R.id.etPass);
		mEtCode = (EditText)findViewById(R.id.etCode);
		mEtPassRep = (EditText)findViewById(R.id.etPassRep);

		mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
		mBtnSubmit.setOnClickListener(this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		Log.e("response",jo.toString());

		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			if(!url.contains(ProtocolConst.SMSCODE))
			{
			CommonUtils.showToast(getApplicationContext(), "注册成功");
			finish();
			}else
			{
				mEtCode.setText(dataModel.code);	
			}
		}else
		{
			CommonUtils.showToast(getApplicationContext(), "注册失败");
		}
		
	}
}
