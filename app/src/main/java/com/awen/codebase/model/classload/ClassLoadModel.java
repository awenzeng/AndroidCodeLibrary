package com.awen.codebase.model.classload;

import com.awen.codebase.common.utils.LogUtil;
import com.awen.codebase.model.classload.ClassLoadParent;

/**
 * @ClassName: ClassLoadModel
 * @Author: AwenZeng
 * @CreateDate: 2020/3/12 17:02
 * @Description: 类的加载顺序
 */

public class ClassLoadModel extends ClassLoadParent {

    // 静态变量
    public static String s_StaticField = "子类--静态变量";
    // 变量
    public String s_Field = "子类--变量";
    // 静态初始化块
    static {
        LogUtil.androidLog(TAG,s_StaticField);
        LogUtil.androidLog(TAG,"子类--静态初始化块");
    }
    // 初始化块
    {
        LogUtil.androidLog(TAG,s_Field);
        LogUtil.androidLog(TAG,"子类--初始化块");
    }

    // 构造器
    public ClassLoadModel() {
        LogUtil.androidLog(TAG,"子类--构造器");
        LogUtil.androidLog(TAG,"i=" + i + ",j=" + j);
    }

}
