package com.awen.codebase.activity.group;



import android.app.Activity;
import android.os.Bundle;

import com.awen.codebase.common.ui.ImageEffect;
public class ShadeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(new ImageEffect(this));
	}
}
