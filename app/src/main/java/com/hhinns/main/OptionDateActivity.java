package com.hhinns.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hhinns.adapter.CalendarGridViewAdapter;
import com.hhinns.library.CommonUtils;
import com.hhinns.library.NumberHelper;
import com.hhinns.view.CalendarGridView;

public class OptionDateActivity extends BaseActivity implements OnTouchListener, OnClickListener
{

	private static final int SWIPE_MIN_DISTANCE = 80;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;

	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	GestureDetector mGesture = null;
	private View view = null;
	private String title = null;
	
	private Calendar  selectOld = Calendar.getInstance(); 
	private boolean  ischeckIn = false;
	private boolean  ischeckOut = false;

	
	

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return mGesture.onTouchEvent(event);
	}

	AnimationListener animationListener = new AnimationListener()
	{
		@Override
		public void onAnimationStart(Animation animation)
		{
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
		}

		@Override
		public void onAnimationEnd(Animation animation)
		{
			CreateGirdView();
		}
	};

	class GestureListener extends SimpleOnGestureListener
	{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			try
			{
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
				{
					viewFlipper.setInAnimation(slideLeftIn);
					viewFlipper.setOutAnimation(slideLeftOut);
					viewFlipper.showNext();
					setNextViewItem();

					return true;

				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
				{
					viewFlipper.setInAnimation(slideRightIn);
					viewFlipper.setOutAnimation(slideRightOut);
					viewFlipper.showPrevious();
					setPrevViewItem();

					return true;

				}
			} catch (Exception e)
			{
				// nothing
			}
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e)
		{
			DisplayMetrics metric = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metric);
			int width = metric.widthPixels;
			int pos = gView2.pointToPosition((int) e.getX(), (int) e.getY());
			LinearLayout txtDay = (LinearLayout) gView2
					.findViewById(pos + 5000);
			view = txtDay;
			if (null != view)
			{
				int[] location = new int[2];
				view.getLocationOnScreen(location);
				int x = location[0]
						- CommonUtils.getCalculateSize(OptionDateActivity.this, 123) / 2
						+ view.getWidth() / 2;
				int y = location[1]
						- CommonUtils.getCalculateSize(OptionDateActivity.this, 68);
				if (x < 0)
				{
//					showpop(1);
				} else if ((location[0] + CommonUtils.getCalculateSize(
						OptionDateActivity.this, 123) / 2) > width)
				{
//					showpop(2);
				} else
				{
//					showpop(0);
				}
				if (txtDay != null)
				{
					if (txtDay.getTag() != null)
					{
						java.util.Date date = (java.util.Date) txtDay.getTag();
						
						Calendar caToday = Calendar.getInstance();
						Calendar checkDay = Calendar.getInstance();
						checkDay.setTime(date);
						if(checkDay.get(Calendar.MONTH)==caToday.get(Calendar.MONTH)&&checkDay.get(Calendar.DAY_OF_MONTH)<caToday.get(Calendar.DAY_OF_MONTH))
						{
							return false;
						}else if(checkDay.get(Calendar.MONTH)<caToday.get(Calendar.MONTH))
						{
							return false;
						}
						
						if(!ischeckIn)
						{
						selectOld.setTime(date);
						ischeckIn = true;
						}else
						{
							if(selectOld.get(Calendar.DAY_OF_MONTH)==checkDay.get(Calendar.DAY_OF_MONTH)
									&&checkDay.get(Calendar.MONTH)==selectOld.get(Calendar.MONTH))
							{
								return false;
							}
						calSelected = Calendar.getInstance();
						calSelected.setTime(date);
						ischeckOut = true;
						}
						
						if(calSelected!=null)
						{
						if(checkDay.get(Calendar.MONTH)==caToday.get(Calendar.MONTH)&&calSelected.get(Calendar.DAY_OF_MONTH)>selectOld.get(Calendar.DAY_OF_MONTH))
						{
						gAdapter.setSelectedDate(selectOld,calSelected,
								OptionDateActivity.this);
						gAdapter.notifyDataSetChanged();

						gAdapter1.setSelectedDate(selectOld,calSelected,
								OptionDateActivity.this);
						gAdapter1.notifyDataSetChanged();

						gAdapter3.setSelectedDate(selectOld,calSelected,
								OptionDateActivity.this);
						gAdapter3.notifyDataSetChanged();
						}else if(checkDay.get(Calendar.MONTH)>caToday.get(Calendar.MONTH))
						{
							gAdapter.setSelectedDate(selectOld,calSelected,
									OptionDateActivity.this);
							gAdapter.notifyDataSetChanged();

							gAdapter1.setSelectedDate(selectOld,calSelected,
									OptionDateActivity.this);
							gAdapter1.notifyDataSetChanged();

							gAdapter3.setSelectedDate(selectOld,calSelected,
									OptionDateActivity.this);
							gAdapter3.notifyDataSetChanged();
							
						}else
						{
							selectOld = calSelected;
							gAdapter.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter.notifyDataSetChanged();

							gAdapter1.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter1.notifyDataSetChanged();

							gAdapter3.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter3.notifyDataSetChanged();
						}
						}else
						{
							gAdapter.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter.notifyDataSetChanged();

							gAdapter1.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter1.notifyDataSetChanged();

							gAdapter3.setSelectedDate(selectOld,null,
									OptionDateActivity.this);
							gAdapter3.notifyDataSetChanged();
						}
	
					}
				}
				
				if(ischeckIn&&ischeckOut)
				{
					Intent intent = new Intent(OptionDateActivity.this,ReservationActivity.class);
					intent.putExtra("checkIn", CommonUtils.getStringByCalender(selectOld));
					intent.putExtra("checkOut", CommonUtils.getStringByCalender(calSelected));
					setResult(0,intent);
					finish();
				}
				Log.i("TEST", "onSingleTapUp -  pos=" + pos);
			}
			return false;
		}
	}

	private Context mContext = OptionDateActivity.this;
	private GridView title_gView;
	private GridView gView1;
	private GridView gView2;
	private GridView gView3;

	boolean bIsSelection = false;
	private Calendar calStartDate = Calendar.getInstance();
	private Calendar calSelected = null;
	private Calendar calToday = Calendar.getInstance();
	private CalendarGridViewAdapter gAdapter;
	private CalendarGridViewAdapter gAdapter1;
	private CalendarGridViewAdapter gAdapter3;
	private Button btnToday = null;
	private RelativeLayout mainLayout;

	private int iMonthViewCurrentMonth = 0; 
	private int iMonthViewCurrentYear = 0; 
	private int iFirstDayOfWeek = Calendar.MONDAY;

	private static final int mainLayoutID = 88; 
	private static final int titleLayoutID = 77; 
	private static final int caltitleLayoutID = 66; 
	private static final int calLayoutID = 55; 
	
	String[] menu_toolbar_name_array;

	private TextView tv_title = null;
	private Button btn_left = null;
	private TextView tv_date = null;
	private Button btn_last = null;
	private Button btn_next = null;
//	private String select = null;
//	private String id = null;

	private String _traveldata = null;
	private ArrayList<HashMap<String, String>> _datelist = null;

	
	private TextView pop_price = null;
	private TextView pop_number = null;
	private PopupWindow pop = null;
	private LinearLayout layout_roof = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date);
//		showpop(0);
//		Intent intent = getIntent();
//		if (null != intent && intent.getStringExtra("id") != null)
//		{
//			select = intent.getStringExtra("select");
//			id = intent.getStringExtra("id");
//			title = intent.getStringExtra("title");
//			_traveldata = intent.getStringExtra("tips");
			initTravelData("");
//		} else
//		{
//			this.finish();
//		}
//		initTravelData(_traveldata);
		tv_title = (TextView) findViewById(R.id.tvTitle);
		tv_title.setText(R.string.in_date);
		btn_left = (Button) findViewById(R.id.btnLeft);
		btn_left.setOnClickListener(this);
//		btn_right.setBackgroundResource(R.drawable.btn_first);
//		btn_right.setOnClickListener(this);
		tv_date = (TextView) findViewById(R.id.tv_date);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_last.setOnClickListener(this);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);
		layout_roof = (LinearLayout) findViewById(R.id.layout_roof);

		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_hodometer);


		layout.addView(generateContentView());
		UpdateStartDateForMonth();

		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		slideRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.push_right_out);

		slideLeftIn.setAnimationListener(animationListener);
		slideLeftOut.setAnimationListener(animationListener);
		slideRightIn.setAnimationListener(animationListener);
		slideRightOut.setAnimationListener(animationListener);

		mGesture = new GestureDetector(this, new GestureListener());
	}

	private void initTravelData(String __traveldata)
	{
		// TODO Auto-generated method stub
		String[] _tiplist = null;
		HashMap<String, String> map = null;
		try
		{
			_datelist = new ArrayList<HashMap<String, String>>();
			_tiplist = __traveldata.split("[`]");
			for (int i = 0; i < _tiplist.length; i++)
			{
				map = new HashMap<String, String>();
				map.put("_date", _tiplist[i].split("[|]")[0] == null ? ""
						: _tiplist[i].split("[|]")[0]);
				map.put("_number", _tiplist[i].split("[|]")[1] == null ? ""
						: _tiplist[i].split("[|]")[1]);
				map.put("_price", _tiplist[i].split("[|]")[2] == null ? ""
						: _tiplist[i].split("[|]")[2]);
				_datelist.add(map);
			}

		} catch (Exception e)
		{
		}
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.btnLeft:
			finish();
			break;
		case R.id.btn_last:
			viewFlipper.setInAnimation(slideRightIn);
			viewFlipper.setOutAnimation(slideRightOut);
			viewFlipper.showPrevious();
			setPrevViewItem();
			break;
		case R.id.btn_next:
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showNext();
			setNextViewItem();
			break;
		}

	}

//	private void showpop(int i)
//	{
//
//		View view = LayoutInflater.from(this).inflate(R.layout.poptrip, null);
//		view.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				if (pop != null && pop.isShowing())
//				{
//					pop.dismiss();
//				}
//				if (layout_roof.getVisibility() == View.VISIBLE)
//				{
//					layout_roof.setVisibility(View.GONE);
//				}
//			}
//		});
//		if (i == 0)
//		{
//			view.setBackgroundResource(R.drawable.pop_bg);
//		}
//		if (i == 1)
//		{
//			view.setBackgroundResource(R.drawable.pop_bg_left);
//		}
//		if (i == 2)
//		{
//			view.setBackgroundResource(R.drawable.pop_bg_right);
//		}
//
//		pop_price = (TextView) view.findViewById(R.id.pop_price);
//		pop_number = (TextView) view.findViewById(R.id.pop_number);
//		pop = new PopupWindow(view,
//				CommonUtils.getCalculateSize(OptionDateActivity.this, 123),
//				CommonUtils.getCalculateSize(OptionDateActivity.this, 68), true);
//		pop.setBackgroundDrawable(new BitmapDrawable());
//		pop.setOnDismissListener(new OnDismissListener()
//		{
//
//			@Override
//			public void onDismiss()
//			{
//				// TODO Auto-generated method stub
//				// pop.setFocusable(false);
//
//				SimpleDateFormat sim = new SimpleDateFormat(
//						"yyyy-MM-dd hh:mm:ss");
//				String str = "1977-1-1 14:40:50";
//				java.util.Date date = null;
//				try
//				{
//					date = sim.parse(str);
//				} catch (ParseException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if (layout_roof.getVisibility() == View.VISIBLE)
//				{
//					layout_roof.setVisibility(View.GONE);
//				}
//				calSelected.setTime(date);
//				gAdapter.setSelectedDate(calSelected, OptionDateActivity.this);
//				gAdapter.notifyDataSetChanged();
//
//				gAdapter1.setSelectedDate(calSelected, OptionDateActivity.this);
//				gAdapter1.notifyDataSetChanged();
//
//				gAdapter3.setSelectedDate(calSelected, OptionDateActivity.this);
//				gAdapter3.notifyDataSetChanged();
//			}
//		});
//	}

	AlertDialog.OnKeyListener onKeyListener = new AlertDialog.OnKeyListener()
	{

		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
		{
			if (keyCode == KeyEvent.KEYCODE_BACK)
			{
//				Intent intent = new Intent(OptionDateActivity.this, Detail.class);
//				intent.putExtra("select", "0");
//				intent.putExtra("id", id);
//				startActivity(intent);
//				finish();
			}
			return false;

		}

	};

	
	private View generateContentView()
	{
		
		viewFlipper = new ViewFlipper(this);
		viewFlipper.setId(calLayoutID);
	
		viewFlipper.setBackgroundColor(R.color.detail_date_bg);
		mainLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params_main = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mainLayout.setLayoutParams(params_main);
		mainLayout.setId(mainLayoutID);

		mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

	
		LinearLayout layTopControls = createLayout(LinearLayout.HORIZONTAL);
	
		generateTopButtons(layTopControls);
		RelativeLayout.LayoutParams params_title = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_title.topMargin = 5;

		layTopControls.setId(titleLayoutID);
		

		calStartDate = getCalendarStartDate();

		setTitleGirdView();
		RelativeLayout.LayoutParams params_cal_title = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_cal_title.addRule(RelativeLayout.BELOW, titleLayoutID);

		mainLayout.addView(title_gView, params_cal_title);

		CreateGirdView();

		RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_cal.addRule(RelativeLayout.BELOW, caltitleLayoutID);

		mainLayout.addView(viewFlipper, params_cal);

		LinearLayout br1 = new LinearLayout(this);
		RelativeLayout.LayoutParams params_br1 = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 1);
		params_br1.addRule(RelativeLayout.BELOW, caltitleLayoutID);
		
		br1.setBackgroundColor(getResources().getColor(R.color.color_2));
		mainLayout.addView(br1, params_br1);

		LinearLayout br = new LinearLayout(this);
		RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 1);
		params_br.addRule(RelativeLayout.BELOW, calLayoutID);
		
		br.setBackgroundColor(getResources().getColor(R.color.color_2));
		mainLayout.addView(br, params_br);

		return mainLayout;

	}

	
	private LinearLayout createLayout(int iOrientation)
	{
		LinearLayout lay = new LinearLayout(this);
		LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
	
		
		lay.setLayoutParams(params);
		
		lay.setOrientation(iOrientation);
		lay.setGravity(Gravity.CENTER);
		return lay;
	}

	
	private void generateTopButtons(LinearLayout layTopControls)
	{
		
		btnToday = new Button(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		btnToday.setLayoutParams(lp);
		btnToday.setTextSize(25);
		btnToday.setBackgroundResource(Color.TRANSPARENT);//

		
		btnToday.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View arg0)
			{
				setToDayViewItem();
			}
		});

		layTopControls.setGravity(Gravity.CENTER_HORIZONTAL);
		layTopControls.addView(btnToday);

	}

	private void setTitleGirdView()
	{

		title_gView = setGirdView();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		title_gView.setLayoutParams(params);
		title_gView.setVerticalSpacing(0);
		title_gView.setHorizontalSpacing(0);
		TitleGridAdapter titleAdapter = new TitleGridAdapter(this);
		title_gView.setAdapter(titleAdapter);
		title_gView.setId(caltitleLayoutID);
	}

	private void CreateGirdView()
	{

		Calendar tempSelected1 = Calendar.getInstance(); 
		Calendar tempSelected2 = Calendar.getInstance();
		Calendar tempSelected3 = Calendar.getInstance(); 
		tempSelected1.setTime(calStartDate.getTime());
		tempSelected2.setTime(calStartDate.getTime());
		tempSelected3.setTime(calStartDate.getTime());

		gView1 = new CalendarGridView(mContext);
		gView1.setBackgroundColor(getResources().getColor(R.color.color_2));
		gView1.setPadding(0, 1, 0, 0);
		tempSelected1.add(Calendar.MONTH, -1);
		gAdapter1 = new CalendarGridViewAdapter(this, tempSelected1, _datelist);
		gView1.setAdapter(gAdapter1);
		gView1.setId(calLayoutID);

		gView2 = new CalendarGridView(mContext);
		gView2.setBackgroundColor(getResources().getColor(R.color.color_2));
		gAdapter = new CalendarGridViewAdapter(this, tempSelected2, _datelist);
		gView2.setAdapter(gAdapter);
		gView2.setId(calLayoutID);

		gView3 = new CalendarGridView(mContext);
		gView3.setBackgroundColor(getResources().getColor(R.color.color_2));
		tempSelected3.add(Calendar.MONTH, 1);
		gAdapter3 = new CalendarGridViewAdapter(this, tempSelected3, _datelist);
		gView3.setAdapter(gAdapter3);
		gView3.setId(calLayoutID);

		gView2.setOnTouchListener(this);
		gView1.setOnTouchListener(this);
		gView3.setOnTouchListener(this);

		if (viewFlipper.getChildCount() != 0)
		{
			viewFlipper.removeAllViews();
		}

		viewFlipper.addView(gView2);
		viewFlipper.addView(gView3);
		viewFlipper.addView(gView1);

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);

		btnToday.setText(s);
	}

	private GridView setGirdView()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		GridView gridView = new GridView(this);
		gridView.setSelector(R.color.transparent);
		gridView.setLayoutParams(params);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setBackgroundColor(getResources().getColor(R.color.color_2));
		
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int i = display.getWidth() / 7;
		int j = display.getWidth() - (i * 7);
		int x = j / 2;
		gridView.setPadding(0, 0, 0, 0);

		return gridView;
	}

	
	private void setPrevViewItem()
	{
		iMonthViewCurrentMonth--;
		
		if (iMonthViewCurrentMonth == -1)
		{
			iMonthViewCurrentMonth = 11;
			iMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1); 
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth); 
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear); 
		tv_date.setText(iMonthViewCurrentYear + "年"
				+ (iMonthViewCurrentMonth + 1) + "月");
	}

	private void setToDayViewItem()
	{

		selectOld.setTimeInMillis(calToday.getTimeInMillis());
		selectOld.setFirstDayOfWeek(iFirstDayOfWeek);
		calStartDate.setTimeInMillis(calToday.getTimeInMillis());
		calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);

	}

	private void setNextViewItem()
	{
		iMonthViewCurrentMonth++;
		if (iMonthViewCurrentMonth == 12)
		{
			iMonthViewCurrentMonth = 0;
			iMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		tv_date.setText(iMonthViewCurrentYear + "年"
				+ (iMonthViewCurrentMonth + 1) + "月");
	}

	private void UpdateStartDateForMonth()
	{
		calStartDate.set(Calendar.DATE, 1);
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);
		btnToday.setText(s);

		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY)
		{
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY)
		{
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
		tv_date.setText(iMonthViewCurrentYear + "年"
				+ (iMonthViewCurrentMonth + 1) + "月");
	}

	private Calendar getCalendarStartDate()
	{
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (selectOld.getTimeInMillis() == 0)
		{
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else
		{
			calStartDate.setTimeInMillis(selectOld.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		return calStartDate;
	}

	public class TitleGridAdapter extends BaseAdapter
	{
		int[] titles = new int[] { R.string.Sun, R.string.Mon, R.string.Tue,
				R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat };

		private Activity activity;

		public TitleGridAdapter(Activity a)
		{
			activity = a;
		}

		@Override
		public int getCount()
		{
			return titles.length;
		}

		@Override
		public Object getItem(int position)
		{
			return titles[position];
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LinearLayout iv = new LinearLayout(activity);
			TextView txtDay = new TextView(activity);
			txtDay.setFocusable(false);
			txtDay.setBackgroundColor(Color.TRANSPARENT);
			iv.setOrientation(1);

			txtDay.setGravity(Gravity.CENTER);
			txtDay.setTextSize(12);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

			int i = (Integer) getItem(position);

			txtDay.setTextColor(R.color.date_title_color);
			Resources res = getResources();

			if (i == R.string.Sat)
			{
				txtDay.setBackgroundColor(res.getColor(R.color.title_text_6));
			} else if (i == R.string.Sun)
			{
				txtDay.setBackgroundColor(res.getColor(R.color.title_text_7));
			} else
			{

			}

			txtDay.setText((Integer) getItem(position));
			iv.setBackgroundColor(res.getColor(R.color.title_text_7));
			iv.addView(txtDay, lp);

			return iv;
		}
	}
	
	

}
