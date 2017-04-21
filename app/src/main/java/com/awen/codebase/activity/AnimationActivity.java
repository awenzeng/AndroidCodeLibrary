package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.awen.codebase.R;

public class AnimationActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	private Button button_toTestAnimation,testAnimationButton;
	private Button bt_last, bt_replay, bt_next;
	protected Animation animation;
	private int[] animationID;
	private int index = 0;             //游标指示

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_animation);
		
		initData();
		initView();
	}
	
    private void initData(){
    	animationID = new int[] { R.anim.myanimation_simple, R.anim.my_alpha_action,
				R.anim.my_scale_action, R.anim.my_translate_action,
				R.anim.my_rotate_action, R.anim.alpha_scale,
				R.anim.alpha_translate, R.anim.alpha_rotate,
				R.anim.scale_translate, R.anim.scale_rotate,
				R.anim.translate_rotate, R.anim.alpha_scale_translate,
				R.anim.alpha_scale_rotate, R.anim.alpha_translate_rotate,
				R.anim.scale_translate_rotate,R.anim.alpha_scale_translate_rotate,
				R.anim.myown_design };
    }

	private void initView() {
		// TODO Auto-generated method stub
		button_toTestAnimation = (Button) findViewById(R.id.Button01);
		testAnimationButton = (Button) findViewById(R.id.Button02);
		bt_last = (Button) findViewById(R.id.button_Last);
		bt_replay = (Button) findViewById(R.id.button_Replay);
		bt_next = (Button) findViewById(R.id.button_Next);
		bt_last.setOnClickListener(this);
		bt_replay.setOnClickListener(this);
		bt_next.setOnClickListener(this);
	}


	private void loadStartAnimation(int i) {

		int itemID = animationID[i];
		if (itemID != 0) {
			animation = AnimationUtils.loadAnimation(this, itemID);
			button_toTestAnimation.startAnimation(animation);
			testAnimationButton.startAnimation(animation);
		}
	}

	private void toLastIndex() {
		--index;
		if (index < 0) {
			index = animationID.length - 1;
		}
		
	}

	private void toNextIndex() {
		++index;
		if (index >= animationID.length) {
			index = 0;
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_Last: {
				toLastIndex();
			}
				break;
			case R.id.button_Next: {
				toNextIndex();
			}
				break;
			default:
				break;
			}
			loadStartAnimation(index);
		
	}

}