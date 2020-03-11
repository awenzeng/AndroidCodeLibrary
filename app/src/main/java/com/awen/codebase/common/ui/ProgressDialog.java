package com.awen.codebase.common.ui;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.awen.codebase.R;


public class ProgressDialog extends Dialog {
    private CharSequence mMessage;
    
    public ProgressDialog(Context context) {
        super(context);
    }

    ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    ProgressDialog(Context context, boolean cancelable,
            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window dialogWindow = getWindow();
//      Display d = dialogWindow.getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
//      p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//      p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        super.onCreate(savedInstanceState);
        
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_dialog, null);
        
        if (!TextUtils.isEmpty(mMessage)) {
            TextView textView = (TextView) view.findViewById(R.id.msg);
            textView.setText(mMessage);
        }
        setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }
    
    public static ProgressDialog show(Context context, CharSequence msg) {
        return show(context, "Loading...", false);
    }
    
    public static ProgressDialog show(Context context, int id) {
        return show(context, context.getResources().getString(id), false);
    }
    
    public static ProgressDialog show(Context context, CharSequence msg, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    public void setMessage(CharSequence msg) {
        mMessage = msg;
    }

}
