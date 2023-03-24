package com.wxw.algorithm.sort;

import java.util.Arrays;

public class SortAlgorithmUtils {

    /**
     * 插入排序
     *
     * 将待排序序列分为两部分，左边是有序部分，右边是待排序部分。
     * 每次从待排序部分选择一个元素，与有序部分依次比较，依次交换位置，直到插入到合适的位置。
     *
     * 时间复杂度：n²
     *  ——  [1000] 耗时：3 ms
     *  ——  [200000] 耗时：3656 ms
     * 空间复杂度：1
     */
    public static int[] chaRuSort(int[] target) {
        if (target == null || target.length == 0) {
            return target;
        }
        for (int i = 1; i < target.length; i++) {
            int current = target[i];
            int k = i - 1;
            while (k >= 0 && target[k] > current) {
                target[k + 1] = target[k];
                k--;
            }
            target[k + 1] = current;
        }

        return target;
    }

    /**
     * 选择排序
     *
     * 分为有序区和无序区两部分，每次将无序区最小或最大的元素插入到有序区的末尾。
     *
     * 时间复杂度：n²
     *      ——  [1000] 耗时：3 ms
     *      ——  [200000] 耗时：23473 ms
     * 空间复杂度：1
     */
    public static int[] xuanZeSort(int[] target) {
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
                    swap(target, left, right);
                }
                left++;
            }
            //有序区在尾部
            right--;
        }
        return target;
    }

    /**
     * 冒泡排序
     *
     * 选择待排序序列末尾的元素，与前一个元素X对比，通过交换位置，每次将最大或者最小的元素移动到一端。
     *
     * 时间复杂度：n²
     *  —— [1000] 耗时：4 ms
     *  —— [200000] 耗时：68612 ms
     * 空间复杂度：1
     */
    public static int[] maoPaoSort(int[] target) {
        if (target == null || target.length < 2) {
            return target;
        }
        //外层循环：n次
        for (int i = 0; i < target.length; i++) {
            boolean isChange = false;
            //内层循环。优化点：数组尾端的元素已经是最大值了，不需要再对比
            for (int j = 1; j < target.length - i; j++) {
                //交换位置，大的向数组尾部走
                if (target[j] < target[j-1]) {
                    swap(target, j, j-1);
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
     * 快速排序
     *
     * 分治思想。选择一个基准点，将比它大的放到右边，小的放到左边，然后再递归对左右做同样地处理。
     * 核心：
     *      需要从待处理区域的两端同时操作，如果要实现左小右大，
     *      选择第一个为基准点
     *      先从右边开始往左遍历：找到第一个比基准点小的元素，交换位置；
     *      再切换到从左到右遍历：找到第一个比基准点大的元素，交换位置；
     *      即可实现左小右大
     *
     *      递归的核心：每个需要排序区间的左右的界限点。
     *          左边递归：quickSort(array, left, mid - 1)
     *          右边递归：quickSort(array, mid + 1, right)
     *
     * 时间复杂度：nLogn
     *  —— [1000] 耗时：1 ms
     *  —— [200000] 耗时：26 ms
     * 空间复杂度：1
     */
    public static int[] kuaiSuSort(int[] target) {
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
                swap(target, right, mid);
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
                swap(target, left, mid);
                mid = left;
                left++;
            }
        }
        //处理左边小的
        quickSort(target, start, mid - 1);
        //处理右边大的
        quickSort(target, mid + 1, end);

    }

    /**
     * 归并排序
     *
     * 分治思想。将待排序序列划分为两个部分，再对左右依次递归划分，直到不能分为止。
     * 然后将左右两个小区间合并为一个有序的大区间。
     * 核心：
     *      核心在于，将两个有序的区间合并成大区间时，可以选择更加高效的方式，
     *      比如采用插入排序合并两个区间时，再右边最小的元素比左边最大的元素大时，可以直接结束排序。
     *
     * 时间复杂度：NLogN
     *  —— [1000] 耗时：7 ms
     *  —— [200000] 耗时：13125 ms
     * 空间复杂度：1
     */
    public static int[] guiBingSort(int[] target) {
        mergeSort(target, 0, target.length - 1);
        return target;
    }

    private static void mergeSort(int[] target, int left, int right) {
        //递归出口
        if (left >= right) {
            return;
        }
        //分成两边
        int mid = (left + right) / 2;
        //左边递归
        mergeSort(target, left, mid);
        //右边递归
        mergeSort(target, mid + 1, right);
        //左右合并排序，右边最小的往左边插入
        int rightMin = mid + 1;

        while (rightMin <= right) {
            int leftMix = rightMin;
            if (target[leftMix] > target[leftMix - 1]) {
                //右边最小比左边最大的大时，可以直接终止循环
                break;
            }
            while (leftMix > left) {
                if (target[leftMix] < target[leftMix - 1]) {
                    swap(target, leftMix, leftMix -1);
                } else {
                    //比左边最大的大时，可以直接终止循环
                    break;
                }
                leftMix--;
            }
            rightMin++;
        }

    }

    /**
     * 堆排序
     * （小根堆）每次取堆顶最小元素，再重新构建堆，始终保持堆顶元素最小。
     *
     * 小根堆：父节点一定比字节点小，堆顶元素就是最小的元素。
     * 堆构建：
     *      将堆构建成一个小根堆。
     *      在数组中的父子节点存在的表示关系：
     *          设父节点为i，那么
     *              左子节点就是：(i+1)*2 - 1;
     *              右子节点就是：(i+1)*2;
     *
     *          设子节点为i，那么
     *              父节点就是：(i+1)/2 - 1;
     *
     * 堆重构：（主要耗时过程）每次取出了堆顶元素后，需要比较两个子节点值，将小的元素放到堆顶，
     *      此时子节点对应的树结构就被破坏了，需要递归的去比较子节点值，重新构建树。
     *
     * 时间复杂度：nLogn
     *  —— [1000] 耗时：1 ms
     *  —— [200000] 耗时：50 ms
     * 空间复杂度：
     */
    public static int[] duiSort(int[] target) {
        int[] result = new int[target.length];
        //构建小根堆
        createPile(target);
        //输出堆
        for (int i = 0; i < result.length; i++) {
            //堆顶元素
            result[i] = target[0];
            target[0] = -1;
            //重新构建堆
            reBuildPile(target, 0);
        }
        return result;
    }

    private static void createPile(int[] target) {
        for (int i = 1; i < target.length; i++) {
            int k = i;
            //与父节点比较大小，小根堆，所以比父节点小则交换位置
            while (k != 0) {
                //计算父节点位置
                int parent = (k+1)/2 - 1;
                //k节点值大于父节点值退出循环
                if (target[k] > target[parent]) {
                    break;
                }
                //交换位置
                swap(target, k, parent);
                //指向父节点位置
                k = parent;
            }
        }
    }

    private static void reBuildPile(int[] pile, int i) {
        //最后一个节点
        if (i >= pile.length-1) {
            return;
        }
        int left = (i+1)*2 - 1;
        int right = (i+1)*2;
        if (right < pile.length && pile[right] != -1
                && (pile[left] == -1 || pile[left] > pile[right])) {
            //与父节点交换
            swap(pile, i, right);
            //递归
            reBuildPile(pile, right);
        } else if (left < pile.length && pile[left] != -1) {
            //与父节点交换
            swap(pile, i, left);
            //递归
            reBuildPile(pile, left);
        }
    }

    /**
     * 希尔排序
     *
     * 一个对于插入排序进行优化的排序算法。
     * 先将无序序列分组，分组依据是，相隔一段长度的序列视为一组，然后对这一组数据进行插入排序，
     * 然后缩短分组的间隔，再重新分组，再对组内数据进行插入排序；
     * 以此循环，直到间隔为1时，再执行一次插入排序就完成了排序。
     *
     * 核心：
     *      由于是两个有序的组进行插入排序，将减少很多换位步骤。
     *
     * 时间复杂度：nLogn
     *  —— [1000] 耗时：1 ms
     *  —— [200000] 耗时：48 ms
     * 空间复杂度：
     */
    public static int[] xiErSort(int[] target) {
        if (target == null || target.length <= 1) {
            return target;
        }
        //计算初始的分组大小
        int growSize = target.length / 2;
        //外循环控制分组增长
        while (growSize > 0) {
            //遍历当前增长值下的所有组
            for (int i = 0; i < growSize; i++) {
                //组内插入排序
                for (int j = i + growSize; j < target.length; j = j + growSize) {
                    int k = j - growSize;
                    //比 target[j] 大的元素右移
                    while (k >= 0 && target[k] > target[j]) {
                        //右移
                        target[k + growSize] = target[k];
                        //插入比较指针左移
                        k = k - growSize;
                    }
                    //放入到对应位置
                    target[k + growSize] = target[j];
                }
            }
            //分组合并
            growSize = growSize / 2;
        }
        return target;
    }


    /**
     * 交换数组中 a 和 b 的位置
     */
    public static void swap(int[] target, int a, int b) {
        int temp = target[a];
        target[a] = target[b];
        target[b] = temp;
    }


    public static void main(String[] args) {
//        System.out.println(Arrays.toString(chaRuSort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7})));
//        System.out.println(Arrays.toString(maoPaoSort(new int[]{1, 4, 3, 2, 5, 6, 0, 9, 3, 2})));
//        System.out.println(Arrays.toString(xuanZeSort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7})));
//        System.out.println(Arrays.toString(kuaiSuSort(new int[]{1, 4, 3, 2, 5, 9, 8, 6, 7, 0, 1,1,1,2,3,4,5,7,5,10})));
//        System.out.println(Arrays.toString(guiBingSort(new int[]{11, 4, 32, 2, 5, 9, 8, 6, 7, 0, 1,1,1,2,3,4,5,7,5,10})));
//        System.out.println(Arrays.toString(duiSort(new int[]{11, 4, 32, 2, 5, 9, 8, 6, 7, 0, 1,1,1,2,3,4,5,7,5,10})));
        System.out.println(Arrays.toString(xiErSort(new int[]{11, 4, 32, 2, 5, 9, 8, 6, 7, 0, 1, 1, 1, 2, 3, 4, 5, 7, 5, 10})));

        testLess();
        testLarge();
    }

    public static void testLess() {
        int[] target = new int[1000];
        for (int i = 0; i < target.length; i++) {
            target[i] = (int) (Math.random() * 10000);
        }
        long start = System.currentTimeMillis();


//        chaRuSort(target);
//        maoPaoSort(target);
//        xuanZeSort(target);
//        kuaiSuSort(target);
//        guiBingSort(target);
//        duiSort(target);
        xiErSort(target);

        long end = System.currentTimeMillis();
        System.out.println(checkIsSort(target) +" ["+target.length+"] 耗时：" + (end - start) + " ms");
    }

    public static void testLarge() {
        int[] target = new int[200000];
        for (int i = 0; i < target.length; i++) {
            target[i] = (int) (Math.random() * 10000);
        }
        long start = System.currentTimeMillis();


//        chaRuSort(target);
//        maoPaoSort(target);
//        xuanZeSort(target);
//        kuaiSuSort(target);
//        guiBingSort(target);
//        duiSort(target);
        xiErSort(target);

        long end = System.currentTimeMillis();
        System.out.println(checkIsSort(target) +" ["+target.length+"] 耗时：" + (end - start) + " ms");
    }

    private static boolean checkIsSort(int[] target) {
        for (int i = 1; i < target.length; i++) {
            if (target[i] < target[i-1]) {
                return false;
            }
        }
        return true;
    }
}
