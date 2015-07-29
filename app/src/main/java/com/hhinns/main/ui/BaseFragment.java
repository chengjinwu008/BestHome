package com.hhinns.main.ui;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	protected Activity mActivity = null;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(getActivity());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(getActivity()); // 统计时长
	}
}
