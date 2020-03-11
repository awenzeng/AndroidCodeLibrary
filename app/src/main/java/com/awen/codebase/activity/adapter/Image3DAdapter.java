package com.awen.codebase.activity.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.awen.codebase.R;
import com.awen.codebase.common.ui.Image3DView;
import com.awen.codebase.common.utils.ScreenSizeUtil;

public class Image3DAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] Imgid = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5 };

	public Image3DAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return Imgid.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Image3DView i = new Image3DView(mContext);
		i.setImageResource(Imgid[position]);
		i.setLayoutParams(new Gallery.LayoutParams(ScreenSizeUtil.dp2px(160), ScreenSizeUtil.dp2px(240)));
		i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
		drawable.setAntiAlias(true);
		return i;
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
}