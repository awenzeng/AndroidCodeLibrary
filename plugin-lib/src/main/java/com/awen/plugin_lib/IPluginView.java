package com.awen.plugin_lib;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

public interface IPluginView {
    View getPluginView(Context context, Resources resources);
}
