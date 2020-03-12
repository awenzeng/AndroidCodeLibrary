package com.awen.codebase.model.classload;

import com.awen.codebase.common.utils.LogUtil;

/**
 * @ClassName: Parent
 * @Author: AwenZeng
 * @CreateDate: 2020/3/12 17:05
 * @Description:有继承关系的类初始化顺序
 */
public class ClassLoadParent {

    public static final String TAG = "ClassLoad";
    // 静态变量
    public static String p_StaticField = "父类--静态变量";
    protected int i = 1;
    protected int j = 8;
    // 变量
    public String p_Field = "父类--变量";

    // 静态初始化块
    static {
        LogUtil.androidLog(TAG,p_StaticField);
        LogUtil.androidLog(TAG,"父类--静态初始化块");
    }

    // 初始化块
    {
        LogUtil.androidLog(TAG,p_Field);
        LogUtil.androidLog(TAG,"父类--初始化块");
    }

    // 构造器
    public ClassLoadParent() {
        LogUtil.androidLog(TAG,"父类--构造器");
        LogUtil.androidLog(TAG,"i=" + i + ", j=" + j);
        j = 9;
    }
}
