package com.awen.codebase.model.algorithm;

import android.util.Log;

/**
 * @ClassName: 常规排序算法
 * @Author: AwenZeng
 * @CreateDate: 2021/3/18 20:00
 * @Description:
 */
public class CommonSort {
    /**
     * 插入排序：时间复杂度0(n²)
     * @param aa
     * @return
     */
    public static Integer[] insertSort(Integer[] aa){
        long time = System.currentTimeMillis();
        Log.i(SortTestHelper.TAG,"插入排序算法：");
        for(int i = 1;i<aa.length;i++){
            for(int j = i;j > 0;j--){
                if(aa[j] < aa[j-1]){
                    int temp = aa[j];
                    aa[j] = aa[j-1];
                    aa[j-1] = temp;
                }
            }
            SortTestHelper.printResult(aa);
        }
        Log.i(SortTestHelper.TAG,"算法用时：" + (System.currentTimeMillis() - time));
        return aa;
    }


    /**
     * 希尔排序算法
     * @param arr
     * @return
     */
    public static Integer[] shellSort(Integer[] arr){
        long time = System.currentTimeMillis();
        Log.i(SortTestHelper.TAG,"希尔排序算法：");
        int j;
        for (int gap = arr.length / 2; gap >  0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                for (j = i; j >= gap && tmp - arr[j - gap] < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
            SortTestHelper.printResult(arr);
        }
        Log.i(SortTestHelper.TAG,"算法用时：" + (System.currentTimeMillis() - time));
        return arr;
    }

    /**
     * 冒泡排序算法
     * @param aa
     * @return
     */
    public static Integer[] bubbleSort(Integer[] aa){
        long time = System.currentTimeMillis();
        Log.i(SortTestHelper.TAG,"冒泡排序算法：");
        for(int i = 0;i<aa.length;i++){
            for(int j = aa.length - 1;j > 0;j--){
                if(aa[j] < aa[j-1]){
                    int temp = aa[j];
                    aa[j] = aa[j-1];
                    aa[j-1] = temp;
                }
            }
            SortTestHelper.printResult(aa);
        }
        Log.i(SortTestHelper.TAG,"算法用时：" + (System.currentTimeMillis() - time));
        return aa;
    }
}
