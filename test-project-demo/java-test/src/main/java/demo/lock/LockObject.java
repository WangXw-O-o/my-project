package demo.lock;

import java.util.concurrent.locks.*;

public class LockObject {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final Condition condition = reentrantLock.newCondition();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private int number;


    public void testLock(String traceName) {
        reentrantLock.lock();
        System.out.println(traceName + " >>> 加锁");
        try {
            System.out.println(traceName + " 执行开始......");
            Thread.sleep(100);
            System.out.println(traceName + " 执行完毕......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reentrantLock.unlock();
        System.out.println(traceName + " >>> 解锁");
    }

    public synchronized void testSyncLock(String traceName) {
        try {
            System.out.println(traceName + " 执行开始......");
            Thread.sleep(3000);
            System.out.println(traceName + " 执行完毕......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testWait(String traceName) {
        try {
            reentrantLock.lock();
            System.out.println(traceName + " 释放锁，并等待...");
            condition.await();
            System.out.println(traceName + " 被唤醒，执行...");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testWaitAndSignalAll(String traceName) {
        try {
            reentrantLock.lock();
            System.out.println(traceName + " 释放锁，并等待...");
            condition.await();
            System.out.println(traceName + " 被唤醒，执行...");
            condition.signalAll();
            System.out.println(traceName + " 唤醒所有等待线程...");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testSignal(String traceName) {
        reentrantLock.lock();
        condition.signal();
        System.out.println(traceName + " 唤醒等待的线程...");
        reentrantLock.unlock();
    }

    /**
     * 读锁
     * （1）共享——使用读锁锁住的，调用的线程之间不产生互斥
     * （2）当存在写锁时，读操作阻塞，即无法获取读锁，只有写锁被释放了，才能获取读锁
     */
    public void readLock(String traceName) {
        System.out.println(traceName + " 准备获取读锁...");
        readLock.lock();
        System.out.println(traceName + " 获取读锁成功...");
        try {
            System.out.println(traceName + " 读取开始......");
            Thread.sleep(1000);
            System.out.println(traceName + " 读取结束......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readLock.unlock();
    }

    /**
     * 写锁
     * (1) 使用写锁锁住的，调用的线程之间互斥，只有一个线程的锁被释放了，才能继续被访问
     * (2) 当存在写锁时，读锁阻塞
     */
    public void writeLock(String traceName) {
        writeLock.lock();
        try {
            System.out.println(traceName + " 写入开始......");
            Thread.sleep(5000);
            System.out.println(traceName + " 写入结束......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeLock.unlock();
    }
}
