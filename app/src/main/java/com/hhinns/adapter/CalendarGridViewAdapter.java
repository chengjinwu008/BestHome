package com.hhinns.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhinns.main.R;

public class CalendarGridViewAdapter extends BaseAdapter
{

	private Calendar calStartDate = Calendar.getInstance();
	private Calendar calSelected = null; 
	private Calendar calSelectedOut = null; 

	
	private TextView pop_price = null;
	private TextView pop_number = null;
	private PopupWindow pop = null;
	private Context mContext = null;

	private ArrayList<HashMap<String, String>> mdate = null;

	public void setSelectedDate(Calendar cal, Context context)
	{
		calSelected = cal;
		mContext = context;
	}
	public void setSelectedDate(Calendar checkIn,Calendar checkOut, Context context)
	{
		calSelected = checkIn;
		calSelectedOut = checkOut;
		mContext = context;
	}

	private Calendar calToday = Calendar.getInstance(); 
	private int iMonthViewCurrentMonth = 0;
	private int iDayViewCurrentDay = 0;

	
	private void UpdateStartDateForMonth()
	{
		calStartDate.set(Calendar.DATE, 1); 
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		
		iDayViewCurrentDay = calStartDate.get(Calendar.DAY_OF_MONTH);

		
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

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);

	}

	ArrayList<java.util.Date> titles;

	private ArrayList<java.util.Date> getDates()
	{

		UpdateStartDateForMonth();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();
		
		for (int i = 1; i <= 42; i++)
		{
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}

	private Activity activity;
	Resources resources;

	// construct
	public CalendarGridViewAdapter(Activity a, Calendar cal)
	{
		calStartDate = cal;
		activity = a;
		resources = activity.getResources();
		titles = getDates();
//		showpop(a);
	}

	// construct
	public CalendarGridViewAdapter(Activity a, Calendar cal,
			ArrayList<HashMap<String, String>> _mlist)
	{
		calStartDate = cal;
		activity = a;
		mdate = _mlist;
		resources = activity.getResources();
		titles = getDates();
//		showpop(a);
	}

	public CalendarGridViewAdapter(Activity a)
	{
		activity = a;
		resources = activity.getResources();
	}

	@Override
	public int getCount()
	{
		return titles.size();
	}

	@Override
	public Object getItem(int position)
	{
		return titles.get(position);
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
		iv.setId(position + 5000);
		LinearLayout imageLayout = new LinearLayout(activity);
		imageLayout.setOrientation(0);
		iv.setGravity(Gravity.CENTER);
		iv.setOrientation(1);
		iv.setPadding(0, 10, 0, 10);
		iv.setBackgroundColor(resources.getColor(R.color.white));

		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		Calendar calTody = Calendar.getInstance();
		calCalendar.setTime(myDate);

		final int iMonth = calCalendar.get(Calendar.MONTH);
		final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
		final int today  = calTody.get(Calendar.DAY_OF_MONTH);

	
		iv.setBackgroundColor(resources.getColor(R.color.white));

		TextView txtToDay = new TextView(activity);
		txtToDay.setGravity(Gravity.CENTER);
		txtToDay.setTextSize(9);

	
		if (null!=calSelected&&equalsDate(calSelected.getTime(), myDate))
		{   
			iv.setBackgroundColor(resources.getColor(R.color.selection));
		} 
		
		if(null!=calSelectedOut&&equalsDate(calSelectedOut.getTime(), myDate))
		{
			iv.setBackgroundColor(resources.getColor(R.color.selection));	
		}
		TextView txtDay = new TextView(activity);
		txtDay.setGravity(Gravity.CENTER);

		TextView text = null;
		if (iMonth == iMonthViewCurrentMonth)
		{
			if(iDay>=today)
			{
			txtDay.setTextColor(resources.getColor(R.color.Text_Month));
			}else
			{
			txtDay.setTextColor(resources.getColor(R.color.noMonth));
			}
		} else
		{
			txtDay.setTextColor(resources.getColor(R.color.noMonth));
		}

		int day = myDate.getDate(); 
		txtDay.setText(String.valueOf(day));
		txtDay.setId(position + 500);
		iv.setTag(myDate);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		iv.addView(txtDay, lp);
		text = new TextView(activity);
		text.setGravity(Gravity.CENTER);
		text.setVisibility(View.INVISIBLE);
		if (null!=calSelected&&equalsDate(calSelected.getTime(), myDate))
		{
			text.setText("浣忓簵");
			text.setVisibility(View.VISIBLE);
		}
		if (null!=calSelectedOut&&equalsDate(calSelectedOut.getTime(), myDate))
		{
			text.setText("绂诲簵");
			text.setVisibility(View.VISIBLE);
		}
//		txtDay.setTextColor(resources.getColor(R.color.noMonth));
		iv.addView(text, lp);
		

		return iv;
	}

	@Override
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}

	private Boolean equalsDate(Date date1, Date date2)
	{

		if (date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate())
		{
			return true;
		} else
		{
			return false;
		}

	}

//	private void showpop(Context mContext)
//	{
//
//		View view = LayoutInflater.from(mContext).inflate(R.layout.poptrip,
//				null);
//		pop_price = (TextView) view.findViewById(R.id.pop_price);
//		pop_number = (TextView) view.findViewById(R.id.pop_number);
//		pop = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT, true);
//		pop.setBackgroundDrawable(new BitmapDrawable());
//	}

}
