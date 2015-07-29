package com.hhinns.library;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.hhinns.main.OrderActivity;
import com.hhinns.main.R;

public class DateTimePickDialogUtil implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	private CalendarView cvDate;
	private String tvWeek;

	/**
	 * 日期时间弹出选择框构造函数
	 * 
	 * @param activity
	 *            ：调用的父activity
	 * @param initDateTime
	 *            初始日期时间值，作为弹出窗口的标题和日期时间初始值
	 */
	public DateTimePickDialogUtil(Activity activity, String initDateTime) {
		this.activity = activity;
//		this.initDateTime = initDateTime;

	}

	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInintData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "- "
					+ calendar.get(Calendar.HOUR_OF_DAY);
		}

		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		// timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	/**
	 * 弹出日期时间选择框方法
	 * 
	 * @param inputDate
	 *            :为需要设置的日期时间文本编辑框
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final TextView  day,final TextView mon,
			final TextView week ,final boolean isout) {
		LinearLayout dateTimeLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);

		init(datePicker, timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (CommonUtils.isbigthantoday(dateTime)) {
							String days[] = dateTime.split("-");
							if(days.length>2)
							{
							if(!isout)
							{
								if(!dateTime.equals(OrderActivity.search.getOutdate()))
								{
									if(!CommonUtils.isbigthanend(dateTime, OrderActivity.search.getOutdate()))
									{
								OrderActivity.search.setIndate(dateTime);
								week.setText(tvWeek);
								day.setText(days[days.length-1]);
								mon.setText(days[days.length-2]);
									}else
									{
										CommonUtils.showToast(activity, "入住日期必须早于离店日期");
									}
								}else
								{
									CommonUtils.showToast(activity, "入住日期填写不正确");
								}
							}else if(!dateTime.equals(OrderActivity.search.getIndate()))
							{ 
								if(!CommonUtils.isbigthanend(OrderActivity.search.getIndate(), dateTime))
								{
								OrderActivity.search.setOutdate(dateTime);
								week.setText(tvWeek);
								day.setText(days[days.length-1]);
								mon.setText(days[days.length-2]);
								}else
								{
									CommonUtils.showToast(activity, "入住日期必须早于离店日期");
								}
							}else
							{
								CommonUtils.showToast(activity, "入住和离店不能够是同一天");
							}
							}
						}else
						{
							CommonUtils.showToast(activity, "请选择至少大于今天的日期");
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						// inputDate.setText("");
					}
				}).show();
		onDateChanged(null, 0, 0, 0);
		return ad;
	}
	
	public AlertDialog dateTimePickDialog(TextView  day,TextView mon,TextView week)
	{
		LinearLayout dateTimeLayout = (LinearLayout) activity
		.getLayoutInflater().inflate(R.layout.calenderview, null);
		cvDate = (CalendarView)dateTimeLayout.findViewById(R.id.cvDate);
		cvDate.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				System.out.println();
			}
		});
		ad = new AlertDialog.Builder(activity)
		.setTitle(initDateTime)
		.setView(dateTimeLayout)
		.show();

		return null;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateTime = sdf.format(calendar.getTime());
		tvWeek = CommonUtils.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		ad.setTitle(dateTime);
	}

	/**
	 * 实现将初始日期时间2013年03月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		// 将初始日期时间2013年03月02日 16:45 拆分成年 月 日 时 分 秒
		String date = spliteString(initDateTime, "日", "index", "front");
		// String time = spliteString(initDateTime, "日", "index", "back");

		String yearStr = spliteString(date, "年", "index", "front");
		String monthAndDay = spliteString(date, "年", "index", "back");

		String monthStr = spliteString(monthAndDay, "月", "index", "front");
		String dayStr = spliteString(monthAndDay, "月", "index", "back");

		// String hourStr = spliteString(time, ":", "index", "front");
		// String minuteStr = spliteString(time, ":", "index", "back");

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		// int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		// int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		// calendar.set(currentYear, currentMonth, currentDay,
		// currentHour,currentMinute);
		calendar.set(currentYear, currentMonth, currentDay);
		return calendar;
	}

	public static String spliteString(String srcStr, String pattern,
			String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern);
		} else {
			loc = srcStr.lastIndexOf(pattern);
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc);
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length());
		}
		return result;
	}

}
