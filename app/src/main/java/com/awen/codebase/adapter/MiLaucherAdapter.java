package com.awen.codebase.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.awen.codebase.R;
public class MiLaucherAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> lstDate;
	private TextView txtAge;

	public MiLaucherAdapter(Context mContext, ArrayList<String> list) {
		this.context = mContext;
		lstDate = list;
	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		lstDate.add(startPosition, (String) endObject);
		lstDate.remove(startPosition + 1);
		lstDate.add(endPosition, (String) startObject);
		lstDate.remove(endPosition + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.adapter_milaucher, null);
		txtAge = (TextView) convertView.findViewById(R.id.txt_userAge);
		if(lstDate.get(position)==null){
			txtAge.setText("+");
			txtAge.setBackgroundResource(R.drawable.red);
		}
		else if(lstDate.get(position).equals("none")){
			txtAge.setText("");
			txtAge.setBackgroundDrawable(null);
		}else txtAge.setText("Item" + lstDate.get(position));
		return convertView;
	}

}
