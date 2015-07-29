package com.hhinns.main;

import mobi.toms.appupdate.AppCheckUpdate;
import mobi.toms.appupdate.AppCheckUpdate.ParameterBuilder;
import mobi.toms.appupdate.GenericHandleCallBack;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhinns.library.CommonUtils;
import com.hhinns.library.ScreenManager;

public class SetActivity extends BaseActivity implements OnClickListener {

	private Button mBtnLeft = null;
	private Button mBtnExit = null;
	private TextView mTvTitle = null;	
	
	private LinearLayout feedback;
	private LinearLayout update;
	private LinearLayout changeaccount;
	private LinearLayout changepwd;
	private LinearLayout find;
//	private LinearLayout exit;
	private ParameterBuilder mBuilder;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linFeedback:
			CommonUtils.changeActivity(SetActivity.this, FeedbackActivity.class);
			break;
		case R.id.linUpdate:
			initDownload();
			break;
		case R.id.linChangeaccount:
			CommonUtils.changeActivity(SetActivity.this, LoginActivity.class);
			break;
		case R.id.btnExit:
			CommonUtils.saveSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME, "");
			finish();
			ScreenManager.getScreenManager().popAllActivity();
			break;
		case R.id.linChangePWD:
			CommonUtils.changeActivity(SetActivity.this, ChangePwdActivity.class);
			break;
		case R.id.btnLeft:
			finish();
			break;
		case R.id.linFind:
			CommonUtils.changeActivity(SetActivity.this, FindPwdActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seting);
		initComponents();
	}

	private void initComponents() {
		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.set);
		
		feedback = (LinearLayout) findViewById(R.id.linFeedback);
		feedback.setOnClickListener(this);
		update = (LinearLayout) findViewById(R.id.linUpdate);
		update.setOnClickListener(this);
		changeaccount = (LinearLayout) findViewById(R.id.linChangeaccount);
		changeaccount.setOnClickListener(this);
		changepwd = (LinearLayout) findViewById(R.id.linChangePWD);
		changepwd.setOnClickListener(this);
		find =(LinearLayout)findViewById(R.id.linFind);
		find.setOnClickListener(this);
//		exit = (LinearLayout) findViewById(R.id.linExit);
//		exit.setOnClickListener(this);
		
		mBtnExit = (Button)findViewById(R.id.btnExit);
		mBtnExit.setOnClickListener(this);
	}
	
	private void initDownload() {

		mBuilder = new ParameterBuilder();
		mBuilder.ApiUrl = getString(R.string.checkupdate_api_url);
		mBuilder.BuildTitle = getString(R.string.checkupdate_build_title);
		mBuilder.Content = getString(R.string.checkupdate_content);
		mBuilder.ContentEmpty = getString(R.string.checkupdate_content_empty);
		mBuilder.UnavaiableUpdateMsg = getString(R.string.checkupdate_unavaiable_update_msg);
		mBuilder.ButtonConfirm = getString(R.string.checkupdate_button_confirm);
		mBuilder.ButtonCancel = getString(R.string.checkupdate_button_cancel);
		mBuilder.AlertBuilderInstallTitle = getString(R.string.checkupdate_alert_builder_install_title);
		mBuilder.AlertBuilderInstallMsg = getString(R.string.checkupdate_alert_builder_install_msg);
		mBuilder.ProgressDialogTitle = getString(R.string.checkupdate_progress_dialog_title);
		mBuilder.CancelDownloading = getString(R.string.checkupdate_cancel_downloading);
		mBuilder.ConnectionTimeoutMsg = getString(R.string.checkupdate_connection_timeout_msg);
		mBuilder.NetworkUnavailableMsg = getString(R.string.checkupdate_network_unavailable_msg);
		mBuilder.ErrorMsg = getString(R.string.checkupdate_error_msg);
		new AppCheckUpdate(SetActivity.this, getString(R.string.app_id),
				R.drawable.icon_aboutus_n, mBuilder, new GenericHandleCallBack() {
					@Override
					public void downloadFailure() {

					}

					@Override
					public void operateFinished() {

					}
				}).launchUpdate(false);
	}

}
