package com.hhinns.main;
import com.hhinns.library.CommonUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.TextView;

public class FavorableWebDetail  extends BaseActivity implements
OnClickListener {

	private Button mBtnLeft = null;
	private TextView mTvTitle = null;
	private WebView webView =null;
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
		setContentView(R.layout.activity_favorable_web);
		initComponents();
	}
	private void initComponents() {
		String id = null;
		if(null!=getIntent())
		id = getIntent().getStringExtra("id");
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText("优惠活动详情");
		
		webView = (WebView) findViewById(R.id.detail_web);
		webView.setInitialScale(100);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl(id);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
	}

}
