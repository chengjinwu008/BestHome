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
import android.widget.EditText;
import android.widget.TextView;

public class MyInfoEditActivity extends BaseActivity implements OnClickListener,BusinessResponse{

	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;
	
	private EditText etName;
	private EditText etPhone;
	private EditText etAddress;
	private EditText etEmall;
	private EditText etSex;
	
	private  Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo_edit);
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
        mTvTitle.setText("修改我的资料");
		
		etName  =  (EditText)findViewById(R.id.etName);
		etPhone  =  (EditText)findViewById(R.id.etPhone);
		etAddress  =  (EditText)findViewById(R.id.etAddress);
		etEmall  =  (EditText)findViewById(R.id.etEmall);
		etSex = (EditText)findViewById(R.id.etSex);
		
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		
		MyInfoActivity.dataModel.addResponseListener(this);
		initData();
	}
	
	private void initData()
	{
		etName.setText(MyInfoActivity.dataModel.customer.get("name"));
		etPhone.setText(MyInfoActivity.dataModel.customer.get("phone"));
		etAddress.setText(MyInfoActivity.dataModel.customer.get("idcard"));
		etEmall.setText(MyInfoActivity.dataModel.customer.get("email"));
		etSex.setText(MyInfoActivity.dataModel.customer.get("sex"));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;
	case R.id.btnSubmit:
		     if(etName.getText().toString().trim().equals(""))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "修改的姓名不能为空");
		    	 return;
		     }
		     if(etPhone.getText().toString().trim().equals(""))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "修改的手机号不能为空");
		    	 return;
		     }
		     if(etAddress.getText().toString().trim().equals(""))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "修改的身份证不能为空");
		    	 return;
		     }
		     if(etEmall.getText().toString().trim().equals(""))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "修改的邮箱不能为空");
		    	 return;
		     }else if(!CommonUtils.isEmail(etEmall.getText().toString().trim()))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "请填写正确的邮箱号码");
		    	 return;
		     }
		     if(etSex.getText().toString().trim().equals(""))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "修改的性别不能为空");
		    	 return;
		     }else  if(!etSex.getText().toString().trim().equals("男")&&!etSex.getText().toString().trim().equals("女"))
		     {
		    	 CommonUtils.showToast(getApplicationContext(), "性别只能够填写为男或女");
		    	 return; 
		     }
		     MyInfoActivity.dataModel.changeCustomer(etName.getText().toString().trim(),
		    		 etPhone.getText().toString().trim(), etSex.getText().toString().trim(), 
		    		 etAddress.getText().toString().trim(), etEmall.getText().toString().trim());
			break;

		default:
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			finish();
		}else
		{
			CommonUtils.showToast(getApplicationContext(), "修改个人资料失败");
		}
	}

}
