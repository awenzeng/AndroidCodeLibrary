package com.awen.codebase.ui;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.awen.codebase.R;

public class DataAdapter extends BaseAdapter{
	Context mContext;
	public DataAdapter(Context context){
		mContext = context;
	}
	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.grid_item, null);		
		}
		return convertView;
	}
}