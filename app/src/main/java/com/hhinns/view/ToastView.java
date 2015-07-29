package com.hhinns.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hhinns.main.R;


public class ToastView {

	public static Toast toast;
	private int time;
	private Timer timer;

	public ToastView(Context context, String text) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast_view, null);
		TextView t = (TextView) view.findViewById(R.id.toast_text);
		t.setText(text);
		if (toast != null) {
			toast.cancel();
		}
		toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
	}

	public ToastView(Context context, int text) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast_view, null);
		TextView t = (TextView) view.findViewById(R.id.toast_text);
		t.setText(text);
		if (toast != null) {
			toast.cancel();
		}
		toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
	}

	// 璁剧疆toast鏄剧ず浣岖疆
	public void setGravity(int gravity, int xOffset, int yOffset) {
		// toast.setGravity(Gravity.CENTER, 0, 0); //灞呬腑鏄剧ず
		toast.setGravity(gravity, xOffset, yOffset);
	}

	// 璁剧疆toast鏄剧ず镞堕棿
	public void setDuration(int duration) {
		toast.setDuration(duration);
	}

	// 璁剧疆toast鏄剧ず镞堕棿(镊畾涔夋椂闂?
	public void setLongTime(int duration) {
		// toast.setDuration(duration);
		time = duration;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (time - 1000 >= 0) {
					show();
					time = time - 1000;
				} else {
					timer.cancel();
				}
			}
		}, 0, 1000);
	}

	public void show() {
		toast.show();
	}

	public static void cancel() {
		if (toast != null) {
			toast.cancel();
		}
	}

}
