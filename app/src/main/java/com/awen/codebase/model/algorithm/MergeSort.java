package com.awen.codebase.model.algorithm;

import android.util.Log;

import java.util.Arrays;

/**
 * @ClassName: 归并算法
 * @Author: AwenZeng
 * @CreateDate: 2021/3/18 19:07
 * @Description:
 */
public class MergeSort {
    /***
     * 将arr[l...mid]和arr[mid+1...r]两部分进行归并
     */
    private static void merge(Integer[] arr, int l, int mid, int r) {

        Integer[] aux = Arrays.copyOfRange(arr, l, r + 1);

        // 初始化，i指向左半部分的起始索引位置l；j指向右半部分起始索引位置mid+1
        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {

            if (i > mid) {  // 如果左半部分元素已经全部处理完毕
                arr[k] = aux[j - l];
                j++;
            } else if (j > r) {   // 如果右半部分元素已经全部处理完毕
                arr[k] = aux[i - l];
                i++;
            } else if (aux[i - l].compareTo(aux[j - l]) < 0) {  // 左半部分所指元素 < 右半部分所指元素
                arr[k] = aux[i - l];
                i++;
            } else {  // 左半部分所指元素 >= 右半部分所指元素
                arr[k] = aux[j - l];
                j++;
            }
        }
    }

    /***
     * 递归使用归并排序,对arr[l...r]的范围进行排序
      */
    private static void sort(Integer[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        sort(arr, l, mid);
        sort(arr, mid + 1, r);
        // 对于arr[mid] <= arr[mid+1]的情况,不进行merge
        // 对于近乎有序的数组非常有效,但是对于一般情况,有一定的性能损失
        if (arr[mid].compareTo(arr[mid + 1]) > 0)
            merge(arr, l, mid, r);
    }

    /**
     * 归并排序
     * @param arr
     * @return
     */
    public static Integer[] mergeSort(Integer[] arr) {
        long time = System.currentTimeMillis();
        Log.i(SortTestHelper.TAG,"归并排序算法：");
        int n = arr.length;
        sort(arr, 0, n - 1);
        Log.i(SortTestHelper.TAG,"算法用时：" + (System.currentTimeMillis() - time));
        return arr;
    }
}
