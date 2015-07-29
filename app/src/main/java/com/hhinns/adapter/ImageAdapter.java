package com.hhinns.adapter;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import com.hhinns.bitmap.BitmapDisplayConfig;
import com.hhinns.library.BitmapUtils;
import com.hhinns.main.MainActivity;
import com.hhinns.main.OrderActivity;
import com.hhinns.main.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter{  
    public static List<HashMap<String, String>> imageUrls;       
    private Context context;  
    private ImageAdapter self;
    Uri uri;
    Intent intent;
    ImageView imageView;
	private BitmapDisplayConfig con;
	private BitmapUtils bitmap ;
    public ImageAdapter( Context context) {  
        this.imageUrls = MainActivity.dataAd.adList;  
        this.context = context;  
        this.self = this;
        bitmap = new BitmapUtils(context);
		con = new BitmapDisplayConfig();
		con.setLoadingDrawable(context.getResources().getDrawable(
				R.drawable.ic_launcher));
    }  
  
    public int getCount() {  
        return Integer.MAX_VALUE;  
    }  
  
    public Object getItem(int position) {  
        return imageUrls.get(position % imageUrls.size());  
    }  
   
    public long getItemId(int position) {  
        return position;  
    }  
  
    @SuppressWarnings("unused")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
					case 0: {
						self.notifyDataSetChanged();
					}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
			}
		}
	};
    
    public View getView(int position, View convertView, ViewGroup parent) {  
  
        if(convertView==null){  
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_item,null); 
            Gallery.LayoutParams params = new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT,Gallery.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
            convertView.setTag(convertView);  
        }  
      
        imageView = (ImageView) convertView.findViewById(R.id.ivGallery);  
        imageView.setScaleType(ImageView.ScaleType.FIT_XY); 
        if(null!=imageUrls&&imageUrls.size()>0)
        downloadImage(imageView, "http://1.85.8.194/"+imageUrls.get(position%imageUrls.size()).get("pic"));
        return convertView;  
        
    }  
    
	public void downloadImage(ImageView imgView, String url) {
		bitmap.display(imgView, url, con);
	}
}  
