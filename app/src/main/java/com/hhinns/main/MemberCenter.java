package com.hhinns.main;

import com.hhinns.library.CommonUtils;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemberCenter extends BaseActivity implements OnClickListener {

	private LinearLayout linInfo;
	private LinearLayout linOrder;
	private LinearLayout linCollect;
	private LinearLayout linFriend;
	private Button mBtnLeft = null;
	private TextView mTvTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_center);
		initComponents();
	}

	private void initComponents() {

		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setText(R.string.back);
		mBtnLeft.setBackgroundResource(R.drawable.button_back);
		mBtnLeft.setOnClickListener(this);
		mBtnLeft.setVisibility(View.VISIBLE);
		
		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText("会员中心");
		linInfo = (LinearLayout) findViewById(R.id.linInfo);
		linInfo.setOnClickListener(this);
		linOrder = (LinearLayout) findViewById(R.id.linOrder);
		linOrder.setOnClickListener(this);
		linCollect = (LinearLayout) findViewById(R.id.linCollect);
		linCollect.setOnClickListener(this);
		linFriend = (LinearLayout) findViewById(R.id.linFriend);
		linFriend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linInfo:
			if(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME).equals(""))
			{
				CommonUtils.changeActivity(MemberCenter.this, LoginActivity.class);
			}else
			{
			CommonUtils.changeActivity(MemberCenter.this, MyInfoActivity.class);
			}
			break;
		case R.id.linOrder:
			if(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME).equals(""))
			{
				CommonUtils.changeActivity(MemberCenter.this, LoginActivity.class);
			}else
			{
			CommonUtils.changeActivity(MemberCenter.this, OrderListActivity.class);
			}
			break;
		case R.id.linCollect:
			if(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME).equals(""))
			{
				CommonUtils.changeActivity(MemberCenter.this, LoginActivity.class);
			}else
			{
			CommonUtils.changeActivity(MemberCenter.this, HotelCollectListActivity.class);
			}
			break;
		case R.id.linFriend:
			Intent intent = new Intent();  
		    intent.setAction(Intent.ACTION_PICK);  
		    intent.setData(ContactsContract.Contacts.CONTENT_URI);  
		    startActivityForResult(intent, 1);  
			break;
		case R.id.btnLeft:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 switch (requestCode) {  
	       case 1:  
	           if (data == null) {  
	               return;  
	           }  
	           Uri result = data.getData();  
	           String contactId = result.getLastPathSegment();  
	           String phone = "";  
	           String[] projection = new String[] { Phone.CONTACT_ID,Phone.NUMBER};    
	           Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,       
	                projection, // select sentence      
	                Phone.CONTACT_ID + " = ?", // where sentence      
	                new String[] { contactId }, // where values      
	                Phone.CONTACT_ID); // order by    
	             
	           if (null!=cursor&&cursor.moveToFirst()) {  
	               phone = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));  
	               CommonUtils.sendMSG(MemberCenter.this, phone, String.format("亲,发现好星之家酒店的客户端很好用,可以用手机预定酒店," +
		               		"安卓下载地址：%s 苹果下载地址%s", MainActivity.proPhone.andUrl,MainActivity.proPhone.ioUrl));
	           }else
	           {
	        	   CommonUtils.showToast(getApplicationContext(), "获取联系人失败");
	           }
	           break;  
	    }  
	}
	

}
