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

public class ChangePwdActivity extends BaseActivity implements OnClickListener,BusinessResponse {
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	private EditText mEtOldPwd = null;
	private EditText mEtNewPwd = null;
	private EditText mEtAgainPwd = null;
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
			if(mEtOldPwd.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "旧密码不能为空");
				return;
			}
			if(mEtNewPwd.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "新密码不能为空");
				return;
			}
			if(mEtAgainPwd.getText().toString().trim().equals(""))
			{
				CommonUtils.showToast(getApplicationContext(), "请再次输入新密码");
				return;
			}
			if(CommonUtils.validInput(mEtNewPwd.getText().toString().trim(), "^[0-9a-zA-Z]{6,12}$"))
			{
			if(mEtNewPwd.getText().toString().trim().equals(
					mEtAgainPwd.getText().toString().trim()))
			{
			dataModel.changePwd(mEtOldPwd.getText().toString().trim(), 
					mEtNewPwd.getText().toString().trim());
			}else
			{
				CommonUtils.showToast(getApplicationContext(), "两次输入的新密码不相同");
			}
			}else
			{
				CommonUtils.showToast(getApplicationContext(), "请输入6到12位字母和数字组成的密码");
			}
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
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

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.change);

		mEtOldPwd = (EditText) findViewById(R.id.etOldPwd);
		mEtNewPwd = (EditText) findViewById(R.id.etNewPwd);
		mEtAgainPwd = (EditText) findViewById(R.id.etAgainPwd);
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
			CommonUtils.showToast(getApplicationContext(), "修改密码成功");
			finish();
		}else
		{
			CommonUtils.showToast(getApplicationContext(), "修改失败请重试");
		}
	}
	
	
}
