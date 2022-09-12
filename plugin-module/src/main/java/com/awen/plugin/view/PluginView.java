package com.awen.plugin.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.awen.plugin_lib.IPluginView;
import com.awen.plugin_module.R;

public class PluginView implements IPluginView {
    @Override
    public View getPluginView(Context context, Resources resources) {
        Button button = new Button(context);
        button.setText("我是插件Button");
        button.setTextColor(Color.parseColor("#FF69B4"));
        button.setBackground(resources.getDrawable(R.drawable.blur_bg));
        return button;
    }
}
