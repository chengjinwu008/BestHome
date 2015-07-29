package com.hhinns.adapter;

import java.util.HashMap;
import java.util.List;

import com.hhinns.bitmap.BitmapDisplayConfig;
import com.hhinns.library.BitmapUtils;
import com.hhinns.library.CommonUtils;
import com.hhinns.main.R;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {

	private Context context;
	private List<String> data;
	private ViewHolder viewholder;
	private int mWidth = 0;
	private int mHeight = 0;
	private BitmapUtils bitmap = null;
	private BitmapDisplayConfig con;

	public GalleryAdapter(Context c, List<String> list,
			int width, int height) {
		context = c;
		this.data = list;
		this.mWidth = width;
		this.mHeight = height;
		bitmap = new BitmapUtils(context, CommonUtils.IMAGE_PATH);
		con = new BitmapDisplayConfig();
		con.setLoadingDrawable(context.getResources().getDrawable(
				R.drawable.ic_launcher));
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String url = null;
		ImageView imageView = null;
		try {
			if (convertView == null) {
				viewholder = new ViewHolder();
				convertView = new ImageView(context);
				if (convertView instanceof ImageView) {
					ImageView iv = (ImageView) convertView;
					iv.setLayoutParams(new Gallery.LayoutParams(240, 240));
					viewholder.itemImage = iv;
					convertView.setTag(viewholder);
				}
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}
			url = data.get(position);
			if (null != url ) {
				imageView = viewholder.itemImage;
				imageView.setLayoutParams(new Gallery.LayoutParams(240,
						240));

				downLoadImg(imageView, url);
			}
			return imageView;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + ":"
					+ e.getMessage());
			return convertView;
		} finally {
		}
	}

	static final class ViewHolder {
		ImageView itemImage;
	}
	
	private void downLoadImg(ImageView imgView, String url) {
		bitmap.display(imgView, url, con);
	}
	

}
