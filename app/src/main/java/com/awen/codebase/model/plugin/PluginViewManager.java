package com.awen.codebase.model.plugin;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.awen.plugin_lib.IPluginView;

import java.lang.reflect.Constructor;

public class PluginViewManager {

    public static View getPluginView(Context context, Resources resources){
        try{
            Class<?> aClass = PluginManager.getInstance().getClassLoader().loadClass("com.awen.plugin.view.PluginView");
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object obj = constructor.newInstance(new Object[]{});
            IPluginView view = (IPluginView) obj;
            return view.getPluginView(context,resources);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new View(context);
    }
}
