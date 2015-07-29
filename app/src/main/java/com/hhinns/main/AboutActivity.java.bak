package com.hhinns.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.ScreenManager;
import com.hhinns.model.AboutModel;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener ,BusinessResponse{

	private Button mBtnLeft = null;
	private TextView mTvTitle = null;   
	
	private TextView  tvAbout = null;
	private AboutModel dataModel;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLeft:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ScreenManager.getScreenManager().pushActivity(this);
		initComponents();
	}
	private void initComponents()
	{
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.about_us);
		
		tvAbout = (TextView)findViewById(R.id.tvAbout);
		
		dataModel = new AboutModel(this);
		dataModel.addResponseListener(this);
		dataModel.about();
		
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if(null!=responseStatus&&responseStatus.equals("ok"))
		{
			tvAbout.setText("  "+dataModel.about);
		}
	}


}
