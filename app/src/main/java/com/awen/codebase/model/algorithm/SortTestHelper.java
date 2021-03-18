package com.awen.codebase.model.algorithm;


import android.util.Log;


/**
 * 排序测试辅助类
 */
public class SortTestHelper {

    public static final String TAG = "sort";

    /**
     *  生成有n个元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
     */
    public static Integer[] generateRandomArray(int n, int rangeL, int rangeR) {
        assert rangeL <= rangeR;
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++)
            arr[i] = new Integer((int)(Math.random() * (rangeR - rangeL + 1) + rangeL));
        return arr;
    }

    /**
     * 打印日志结果
     * @param aa
     */
    public static void printResult(Integer[] aa){
        StringBuilder stringBuilder = new StringBuilder();
        for( int i = 0 ; i < aa.length ; i ++ ){
            stringBuilder.append(aa[i]+" ");
        }
        Log.i(TAG,stringBuilder.toString());
    }
}