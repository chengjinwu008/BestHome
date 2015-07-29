package com.hhinns.main;

import java.util.TooManyListenersException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hhinns.dataprocess.AjaxStatus;
import com.hhinns.dataprocess.BusinessResponse;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.ProtocolConst;
import com.hhinns.model.ProccessForPhone;
import com.hhinns.model.ProccessForTip;
import com.hhinns.model.RoomModel;
import com.hhinns.protocol.RESERVATION;

public class ReservationActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private Button mBtnLeft = null;
	private Button mBtnRight = null;
	private TextView mTvTitle = null;

	private TextView tvType = null;
	private EditText etNum = null;
	private LinearLayout linDate = null;
	private TextView tvDate = null;
	private TextView tvPrice = null;
	private EditText etName = null;
	private EditText etPhone = null;
	private RadioButton rbCash = null;
	private RadioButton rbPaynet = null;
	private EditText etRemark = null;
	private TextView tvMax = null;

	private RoomModel dataModel = null;
	public RESERVATION res = null;
	private int price;
	private String hotel_id = null;
	private String room_type = null;
	private String maxNum = null;
	
	private String room_type_text = null;
	private String special = null;
	private TextView tvTip = null;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnRight:
			if (etNum.getText().toString().trim().equals("")) {
				CommonUtils.showToast(getApplicationContext(),
						getString(R.string.reservation_erro_num));
				return;
			} else {
				res.setNumber(etNum.getText().toString().trim());
				res.setAmount(dataModel.totle);
			}
			if (etName.getText().toString().trim().equals("")) {
				CommonUtils.showToast(getApplicationContext(),
						getString(R.string.reservation_erro_name));
				return;
			} else {
				res.setmName(etName.getText().toString().trim());
			}
			if (etPhone.getText().toString().trim().equals("")) {
				CommonUtils.showToast(getApplicationContext(),
						getString(R.string.reservation_erro_phone));
				return;
			} else if (CommonUtils.matcherPhone(etPhone.getText().toString()
					.trim())) {
				res.setmPhone(etPhone.getText().toString().trim());
			} else {
				CommonUtils.showToast(getApplicationContext(),
						getString(R.string.reservation_erro_phone_er));
				return;
			}
			if (null == res.getCheckInDate()) {
				CommonUtils.showToast(getApplicationContext(),
						getString(R.string.reservation_erro_date));
				return;
			}
			res.setRemark(etRemark.getText().toString().trim());
			res.setmId(CommonUtils.getSharedPreFerences(
					getApplicationContext(), CommonUtils.SHARED_USER_NAME));
			res.sethId(hotel_id);
			res.setRoomTpyeId(room_type);

			dataModel.reservation(res);
			break;
		case R.id.linDate:
			Intent intent = new Intent(ReservationActivity.this,
					OptionDateActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.rbcash:
			res.setPayType("1");
			break;
		case R.id.rbPaynet:
			res.setPayType("2");
			break;

		default:
			break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservations);
		initComponents();
	}

	private void initComponents() {

		if (null != getIntent()) {
			hotel_id = getIntent().getStringExtra("hotelId");
			room_type = getIntent().getStringExtra("roomType");
			maxNum = getIntent().getStringExtra("maxNum");
			special = getIntent().getStringExtra("special");
			price = (int)Float.parseFloat(getIntent().getStringExtra("price"));
			room_type_text = getIntent().getStringExtra("roomTypeText");
		}
		
//		hotel_id = "2";
//		room_type = "5";
//		maxNum = "3";
//		special = "是";
//		price = 168;
//		room_type_text = "豪华大床";

		mBtnLeft = (Button) findViewById(R.id.btnLeft);
		mBtnLeft.setVisibility(View.GONE);

		mTvTitle = (TextView) findViewById(R.id.tvTitle);
		mTvTitle.setText(R.string.app_name);
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setGravity(Gravity.LEFT);

		mBtnRight = (Button) findViewById(R.id.btnRight);
		mBtnRight.setText(R.string.submit);
		mBtnRight.setVisibility(View.VISIBLE);
		mBtnRight.setBackgroundResource(R.drawable.button_green);
		mBtnRight.setOnClickListener(this);

		tvType = (TextView) findViewById(R.id.tvType);
		etNum = (EditText) findViewById(R.id.etRoomNum);
		etNum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(etNum.getText()
						.toString().trim().equals(""))
				{
//					etNum.setText("1");
				}else if(null!=res.getCheckInDate()&&null!=res.getCheckOutDate())
				{
					dataModel.getTotle(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME), 
							hotel_id, room_type, etNum.getText().toString(), res.getCheckInDate(), res.getCheckOutDate());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		linDate = (LinearLayout) findViewById(R.id.linDate);
		linDate.setOnClickListener(this);
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		etName = (EditText) findViewById(R.id.etName);
		etPhone = (EditText) findViewById(R.id.etPhone);
		rbCash = (RadioButton) findViewById(R.id.rbcash);
		rbCash.setOnClickListener(this);
		rbPaynet = (RadioButton) findViewById(R.id.rbPaynet);
		rbPaynet.setOnClickListener(this);
		etRemark = (EditText) findViewById(R.id.etRemark);
		
		tvTip = (TextView)findViewById(R.id.tvViewTip);
		tvTip.setText(ProccessForTip.contentTip);
		
		tvMax = (TextView)findViewById(R.id.tvMaxNumber);
		tvMax.setText("还有"+maxNum+"间");
		
		if(null!=special&&special.equals("1"))
		{
		tvType.setText("特价￥"+price+room_type_text);
		}else 
		{
			tvType.setText("￥"+price+room_type_text);	
		}
		
		dataModel = new RoomModel(getApplicationContext());
		dataModel.addResponseListener(this);
		res = new RESERVATION();
		res.setPayType("1");
		
		if(!CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_NICKNAME).equals(""))
		{
		etName.setText(CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_NICKNAME));
		res.setmName(CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_NICKNAME));
		}
		if(!CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_PHONE).equals(""))
		{
		etPhone.setText(CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_PHONE));
		res.setmPhone(CommonUtils.getSharedPreFerences(getApplicationContext(),
				CommonUtils.SHARED_USER_PHONE));
		}
		if(null!=OrderActivity.search)
		{
		res.setCheckInDate(OrderActivity.search.getIndate());
		res.setCheckOutDate(OrderActivity.search.getOutdate());
		String checkIn[] = OrderActivity.search.getIndate().split("-");
		String checkOut[] = OrderActivity.search.getOutdate().split("-");
		
		
		String Str = String.format("%s(%s月%s日-%s月%s日)", 
				CommonUtils.getDaysBy(OrderActivity.search.getIndate(), 
						OrderActivity.search.getOutdate()),
						checkIn[1],checkIn[2],checkOut[1],checkOut[2]);
		tvDate.setText(Str);
		
		dataModel.getTotle(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME), 
				hotel_id, room_type, etNum.getText().toString(), this.res.getCheckInDate(), this.res.getCheckOutDate());
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		String responseStatus = jo.optString("status");
		if (null != responseStatus && responseStatus.toLowerCase().equals("ok")) {
			
			if(url.endsWith(ProtocolConst.TOTEL))
			{
				tvPrice.setText(dataModel.totle);
			}else if(url.endsWith(ProtocolConst.RESERCATION))
			{
				finish();
			}else
			{
				Intent intent = new Intent(getApplicationContext(),BannerWebActivity.class);
				intent.putExtra("url", dataModel.pay_url);
				startActivity(intent);
//				finish();
			}
		}
	}

	@Override
	protected void onActivityResult(int arg0, int res, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, res, arg2);
		if (res == 0&&arg2!=null) {
			this.res.setCheckInDate(arg2.getStringExtra("checkIn"));
			this.res.setCheckOutDate(arg2.getStringExtra("checkOut"));
			String checkIn[] = arg2.getStringExtra("checkIn").split("-");
			String checkOut[] = arg2.getStringExtra("checkOut").split("-");
			
			
			String Str = String.format("%s(%s月%s日-%s月%s日)", 
					CommonUtils.getDaysBy(arg2.getStringExtra("checkIn"), 
							arg2.getStringExtra("checkOut")),
							checkIn[1],checkIn[2],checkOut[1],checkOut[2]);
			tvDate.setText(Str);
			
			dataModel.getTotle(CommonUtils.getSharedPreFerences(getApplicationContext(), CommonUtils.SHARED_USER_NAME), 
					hotel_id, room_type, etNum.getText().toString(), this.res.getCheckInDate(), this.res.getCheckOutDate());
		}
	}

}
