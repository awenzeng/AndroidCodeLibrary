package com.awen.codebase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awen.codebase.R;
import com.awen.codebase.ui.explosion.ExplosionField;

public class ExplosionFragment extends Fragment {
	private ExplosionField mExplosionField;

	public ExplosionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_explosion,container);
		mExplosionField = ExplosionField.attach2Window(getActivity());
		addListener(view.findViewById(R.id.root));
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	private void addListener(View root) {
		if (root instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) root;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {
			root.setClickable(true);
			root.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mExplosionField.explode(v);
					v.setOnClickListener(null);
				}
			});
		}
	}

	private void reset(View root) {
		if (root instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) root;
			for (int i = 0; i < parent.getChildCount(); i++) {
				reset(parent.getChildAt(i));
			}
		} else {
			root.setScaleX(1);
			root.setScaleY(1);
			root.setAlpha(1);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mExplosionField.clear();
	}
}
