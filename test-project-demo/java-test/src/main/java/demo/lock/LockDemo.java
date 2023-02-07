package demo.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LockDemo {

    private static final ExecutorService pool_1 = Executors.newFixedThreadPool(5);
    private static final ExecutorService pool_2 = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
//        testReentrantLock();
//        testSyncLock();
//        testWaitAndSignal();
        readAndWriteLockTest();
    }

    private static void testReentrantLock() {
        LockObject lockObject = new LockObject();
        int n = 5;
        while (n-- > 0) {
            pool_1.execute(() -> {
                lockObject.testLock(Thread.currentThread().getName());
            });
        }
        pool_1.shutdown();
    }

    private static void testSyncLock() {
        LockObject lockObject = new LockObject();
        int n = 3;
        while (n-- > 0) {
            pool_2.execute(() -> {
                lockObject.testSyncLock(Thread.currentThread().getName());
            });
        }
    }

    private static void testWaitAndSignal() {
        LockObject lockObject = new LockObject();
        //3个线程进入等待队列，
        //这个线程被唤醒时，可以唤醒所有的等待线程
        new Thread(() -> lockObject.testWaitAndSignalAll(Thread.currentThread().getName())).start();

        //该线程先被唤醒时，其他两个线程会一直等待。所以这个线程不能第一个进入等待队列
        new Thread(() -> lockObject.testWait(Thread.currentThread().getName())).start();

        //这个线程被唤醒时，可以唤醒所有的等待线程
        new Thread(() -> lockObject.testWaitAndSignalAll(Thread.currentThread().getName())).start();

        //设置一个唤醒线程，唤醒等待队列的第一个线程
        new Thread(() -> lockObject.testSignal(Thread.currentThread().getName())).start();
    }

    public static void readAndWriteLockTest() {
        LockObject lockObject = new LockObject();
        int k = 1;
        while (k-- > 0) {
            pool_1.execute(() -> {
                lockObject.writeLock(Thread.currentThread().getName());
            });
        }

        int n = 3;
        while (n-- > 0) {
            pool_1.execute(() -> {
                lockObject.readLock(Thread.currentThread().getName());
            });
        }

        pool_1.shutdown();
    }


}
