package com.awen.codebase.activity.group;

import android.app.Activity;
import android.os.Bundle;

import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.Image3DAdapter;
import com.awen.codebase.common.ui.MyMirrorGallery;

public class Image3DShowActivity extends Activity {
	public MyMirrorGallery gallery;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_3d_show);
		gallery=(MyMirrorGallery)findViewById(R.id.Mygallery);
		gallery.setAdapter(new Image3DAdapter(this));
	}

}