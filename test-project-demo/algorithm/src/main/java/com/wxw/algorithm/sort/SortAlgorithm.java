package com.wxw.algorithm.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class SortAlgorithm {

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(mao_pao_sort(new int[]{1, 4, 3, 2, 5, 6})));
//        System.out.println(Arrays.toString(cha_ru_sort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7})));
//        System.out.println(Arrays.toString(xuan_ze_sort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7})));
        System.out.println(Arrays.toString(kuai_su_sort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7, 0, 1,1,1,2,3,4,5,7,5,10})));
    }

    /**
     * 选择排序
     * 时间复杂度：nlogn
     * 空间复杂度：2n
     */
    public static int[] cha_ru_sort(int[] target) {
        if (target == null || target.length < 2) {
            return target;
        }
        //链表实现：节省数组交换的时间
        LinkedList<Integer> linkedList = new LinkedList<>();
        //放置第一个
        linkedList.addFirst(target[0]);
        for (int i = 1; i < target.length; i++) {
            int size = linkedList.size();
            for (int j = 0; j < size; j++) {
                //给当前值找到对应的位置
                if (target[i] <= linkedList.get(j)) {
                    linkedList.add(j, target[i]);
                    break;
                }
                if (j == size - 1) {
                    linkedList.addLast(target[i]);
                }
            }
        }
        int[] result = new int[linkedList.size()];
        ListIterator<Integer> iterator = linkedList.listIterator();
        for (int i = 0; i < linkedList.size(); i++) {
            result[i] = iterator.next();
        }
        return result;
    }

    /**
     * 冒泡排序
     * 时间复杂度：n²
     * 空间复杂度：1
     */
    public static int[] mao_pao_sort(int[] target) {
        if (target == null || target.length < 2) {
            return target;
        }
        //外层循环：n次
        for (int i = 0; i < target.length; i++) {
            boolean isChange = false;
            //内层循环: n-1次
            for (int j = 1; j < target.length; j++) {
                //交换位置，大的下沉
                if (target[j] > target[j-1]) {
                    int temp = target[j];
                    target[j] = target[j-1];
                    target[j-1] = temp;
                    isChange = true;
                }
            }
            //结束条件：没有发生变化
            if (!isChange) {
                break;
            }
        }
        return target;
    }

    /**
     * 选择排序
     */
    public static int[] xuan_ze_sort(int[] target) {
        if (target == null || target.length < 2) {
            return target;
        }
        int right = target.length - 1;
        for (int i = 0; i < target.length; i++) {
            int left = 0;
            while (left <= right) {
                //找到比最右边大的就交换位置
                if (target[left] > target[right]) {
                    //交换位置
                    int temp = target[left];
                    target[left] = target[right];
                    target[right] = temp;
                }
                left++;
            }
            right--;
        }
        return target;
    }

    /**
     * 快速排序
     */
    public static int[] kuai_su_sort(int[] target) {
        if (target==null || target.length < 2) {
            return target;
        }
        quickSort(target, 0, target.length - 1);
        return target;
    }

    public static void quickSort(int[] target, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = start;
        int left = start + 1;
        int right = end;
        //左边找比mid大的，右边找比mid小的
        while (left <= right) {
            //右边找到第一个比mid小的
            while (target[right] > target[mid] && right > mid) {
                right--;
            }
            if (right == mid) {
                break;
            } else {
                //与小的交换位置
                int temp = target[right];
                target[right] = target[mid];
                target[mid] = temp;
                mid = right;
                right--;
            }

            //左边找到第一个比mid大的
            while (target[left] < target[mid] && left < mid) {
                left++;
            }
            if (left == mid) {
                break;
            } else {
                int temp = target[left];
                target[left] = target[mid];
                target[mid] = temp;
                mid = left;
                left++;
            }
        }
        //处理左边小的
        quickSort(target, start, mid - 1);
        //处理右边大的
        quickSort(target, mid + 1, end);

    }
}
