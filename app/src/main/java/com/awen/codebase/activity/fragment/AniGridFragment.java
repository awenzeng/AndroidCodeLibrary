package com.awen.codebase.activity.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.AniGridAdapter;
import com.awen.codebase.common.data.Configure;
import com.awen.codebase.common.ui.AniGridDragGrid;

public class AniGridFragment extends Fragment{
	private AniGridDragGrid gridView;
	private TranslateAnimation left, right;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anigrid, container);
		 Configure.init(getActivity());
			gridView = (AniGridDragGrid)view.findViewById(R.id.gridview);
			ArrayList<String> l = new ArrayList<String>();
			for (int i = 0; i < 8; i++) {
				l.add("" + i);
			}
			AniGridAdapter adapter = new AniGridAdapter(getActivity(), l);
			gridView.setAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
