package com.awen.codebase.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.awen.codebase.R;

public class PopupWindowFragment extends Fragment implements OnClickListener {
    private PopupWindow mPop;
    private RelativeLayout rlRoot;

    public PopupWindowFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_popupwindow, container);
        rlRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
        Button button = (Button) view.findViewById(R.id.btn1);
        button.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn2);
        button.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn3);
        button.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn4);
        button.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn5);
        button.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn6);
        button.setOnClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initPopMenu(Context context) {
        if (mPop == null) {
            mPop = new PopupWindow(((Activity) context).getLayoutInflater()
                    .inflate(R.layout.pop, null), LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            mPop.setOutsideTouchable(true);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPop != null) {
            mPop.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1: {
                initPopMenu(getContext());
                mPop.showAsDropDown(v);
                break;
            }
            case R.id.btn2: {
                initPopMenu(getContext());
                mPop.showAsDropDown(v, 50, -30);
                break;
            }
            case R.id.btn3: {
                initPopMenu(getContext());
                mPop.showAtLocation(rlRoot, Gravity.CENTER, 0, 0);
                break;
            }
            case R.id.btn4:
                if (mPop != null) {
                    mPop.dismiss();
                }
                break;
            case R.id.btn5: {
                initPopMenu(getContext());
                mPop.showAtLocation(rlRoot, Gravity.TOP | Gravity.LEFT, 20, 20);
                break;
            }
            case R.id.btn6: {
                initPopMenu(getContext());
                // 算法是以 屏幕水平居中|屏幕底部为参照物 ,向上偏移"关闭"按钮的高度
                mPop.showAtLocation(rlRoot, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, v.getHeight());
                break;
            }
            default:
                break;
        }
    }

}
