import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 设置vm参数，modify option中添加VM option项
 */
public class GCDemo {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
//        referenceLevelTest();

//        testAllocation();
//        testPretenureSizeThreshold();
//        testTenuringThreshold();
        testTenuringThreshold2();

    }

    //引用类型
    private static void referenceLevelTest() {
        //强引用
        String strongReference = "强引用";

        System.out.println("----软引用----");
        Object obj_soft = new Object();
        //软引用
        SoftReference<Object> softReference = new SoftReference<>(obj_soft);
        System.out.println(obj_soft);
        System.out.println(softReference.get());
        obj_soft = null;
        System.gc();
        System.out.println(obj_soft);
        System.out.println(softReference.get());

        System.out.println("----弱引用----");
        Object obj_weak = new Object();
        //弱引用
        WeakReference<Object> weakReference = new WeakReference<>(obj_weak);
        System.out.println(obj_weak);
        System.out.println(weakReference.get());
        obj_weak = null;
        System.gc();
        System.out.println(obj_weak);
        System.out.println(weakReference.get());

        System.out.println("----虚引用----");
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        //虚引用
        PhantomReference<String> phantomReference = new PhantomReference<>("虚引用", referenceQueue);
        System.out.println(phantomReference.get());
        System.gc();
        System.out.println(phantomReference.get());

        System.out.println("----强引用----");
        System.out.println(strongReference);
    }

    /**
     * 对象优先在Eden分配
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];  // 出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];  //并未直接分配在老年代中，应当是收集器的原因
    }

    /**
     * 长期存活对象将进入老年代
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:Survivor-Ratio=8
     * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];   // 什么时候进入老年代决定于XX:MaxTenuring-Threshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 动态对象年龄判断
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];  // allocation1+allocation2大于survivo空间一半
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

}
