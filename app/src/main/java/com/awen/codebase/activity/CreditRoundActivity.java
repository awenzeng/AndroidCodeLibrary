package com.awen.codebase.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.awen.codebase.R;
import com.awen.codebase.common.ui.RoundIndicatorView;
import com.awen.codebase.common.utils.FastBlurUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by AwenZeng on 2017/2/28.
 */

public class CreditRoundActivity extends Activity {
    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private Button button;
    private ImageView blurImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_credit_round);
        roundIndicatorView = (RoundIndicatorView) findViewById(R.id.my_view);
        editText = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.btn);
        blurImg = (ImageView)findViewById(R.id.blurImg);
        roundIndicatorView.setCurrentNumAnim(600);
        Observable.just(R.drawable.blur_bg).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer id) {
                return FastBlurUtil.doBlur(BitmapFactory.decodeResource(getResources(), id),10,false);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                blurImg.setImageBitmap(bitmap);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a =Integer.valueOf(editText.getText().toString());
                roundIndicatorView.setCurrentNumAnim(a);
            }
        });
    }

}
