package com.hhinns.main;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class BannerWebActivity extends BaseActivity {

	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payweb);

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText("在线支付");
		mTvTitle.setVisibility(View.VISIBLE);

		webView = (WebView) findViewById(R.id.pay_web);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});

		webView.setInitialScale(25);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);

		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadDataWithBaseURL(null, url, "text/html", "utf-8",null);

	}

	/**
	 * 鍙嬬洘缁熻
	 */

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 缁熻镞堕昵
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
