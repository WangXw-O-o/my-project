package demo.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo {

    private volatile int value;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        threadByExThread();
//        threadByImRunnable();
//        threadWithCallback();
//        threadPool();
//        kindOfThreadPool();
//        testInterrupt();
//        testJoin();
//        testCountDownLatch();
//        testCyclicBarrier();
        testSemaphore();
    }

    /**
     * 继承 Thread 类，重写 run() 方法，调用 start() 方法启动线程。
     */
    private static void threadByExThread() {
        MyThreadExThread myThread = new MyThreadExThread();
        myThread.start();
    }

    /**
     * 实现 Runnable 接口，实现 run() 方法，将实例对象传入 Thread 构造方法，调用 start() 方法启动线程。
     */
    private static void threadByImRunnable() {
        MyThreadImRunnable myThreadImRunnable = new MyThreadImRunnable();
        Thread thread = new Thread(myThreadImRunnable);
        thread.start();
    }

    /**
     * 具有返回值的线程
     * 必须实现 Callable 接口，实现 call() 方法
     * 需要一个 ExecutorService 执行该方法，并获取一个 Future 对象，从 Future 对象中获取返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void threadWithCallback() throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> submit = executorService.submit(myCallable);
        String callbackResult = submit.get();
        System.out.println(callbackResult);
        executorService.shutdown();
    }

    /**
     * 线程池
     */
    private static void threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            //选择一个线程执行
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " running......");
                    try {
                        //当前线程睡眠3秒
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 四种线程池
     *
     * @throws InterruptedException
     */
    private static void kindOfThreadPool() throws InterruptedException {
        System.out.println("pool 1 : ");
        //创建一个可根据需要创建新线程的线程池
        ExecutorService pool_1 = Executors.newCachedThreadPool();
        pool_1.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_1.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_1.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_1.shutdown();



        Thread.sleep(2000);
        System.out.println("pool 2 : ");
        //创建一个固定数量线程的线程池，以共享的无界队列方式运行这些线程
        ExecutorService pool_2 = Executors.newFixedThreadPool(3);
        pool_2.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_2.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_2.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_2.execute(() -> System.out.println(Thread.currentThread().getName() + " running..."));
        pool_2.shutdown();



        Thread.sleep(2000);
        System.out.println("pool 3 : ");
        //创建只包含一个线程的线程池，当线程死亡或者发生异常时，创建一个新的线程来替代原有的线程
        ExecutorService pool_3 = Executors.newSingleThreadExecutor();
        int n = 10;
        while (n-- > 0) {
            int finalN = n;
            pool_3.execute(() -> {
                if (finalN == 5) {
                    //出现异常
                    throw new RuntimeException("出现异常...");
                } else {
                    System.out.println(Thread.currentThread().getName() + " running...");
                }
            });
        }



        Thread.sleep(2000);
        System.out.println("pool 4 : ");
        //创建一个线程池，它可安排在给定的延迟后运行命令，或者定期的执行
        ScheduledExecutorService pool_4 = Executors.newScheduledThreadPool(4);
        System.out.println("计时开始！");
        pool_4.schedule(
                () -> System.out.println(Thread.currentThread().getName() + " 延迟10秒后执行..."),
                10, TimeUnit.SECONDS);

        System.out.println("计时开始！");
        pool_4.scheduleAtFixedRate(
                () -> System.out.println(Thread.currentThread().getName() + " 延迟5秒后，每1秒执行一次..."),
                5, 1, TimeUnit.SECONDS);
    }

    public static void testInterrupt() {
        Thread thread1 = new Thread(() -> {
            //一直运行，直到被中断后退出
            while (true) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " is running...");
                } else {
                    System.out.println(Thread.currentThread().getName() + " is interrupt...");
                    break;
                }
            }
        });
        thread1.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
    }

    public static void testJoin() {
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 2 running...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 2 running...");
        });

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 1 running...");
            try {
                thread2.join();
                System.out.println(Thread.currentThread().getName() + " 1 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }

    /**
     * 程序计数器 CountDownLatch （一个线程等待其他线程完成操作再继续执行）
     * 设置一个计数器，
     * A线程 调用 await() 方法进入等待状态，
     * 其他线程调用 countDown() 减少计数，
     * 当计数器为 0 时，A线程被唤醒继续执行
     */
    public static void testCountDownLatch() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " end...");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " end...");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                System.out.println("等待其他2个线程执行完毕......");
                countDownLatch.await();
                System.out.println("其他2个线程执行完毕......");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end...");
        }).start();
    }

    /**
     * CyclicBarrier 回环栅栏（所有线程都完成前半段操作，再进行后续操作）
     * 所有线程都可以使用 await() 方法进入等待，当等待的线程数达到设定的数量时，所有的线程都被唤醒，继续执行下去
     */
    public static void testCyclicBarrier() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start...");
            System.out.println(Thread.currentThread().getName() + "执行完成，等待其他线程执行完成...");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end...");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start...");
            System.out.println(Thread.currentThread().getName() + "执行完成，等待其他线程执行完成...");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end...");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start...");
            System.out.println(Thread.currentThread().getName() + "执行完成，等待其他线程执行完成...");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end...");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start...");
            System.out.println(Thread.currentThread().getName() + "执行完成，等待其他线程执行完成...");
            try {
                //设置超时时间，如果到达指定时间，到达 Barrier 状态的线程数还不够，那么直接执行下去
                cyclicBarrier.await(3, TimeUnit.SECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end...");
        }).start();
    }

    /**
     * Semaphore 信号量 （类似于锁，控制并发数）
     * 设置线程数——最大许可数
     * 使用 acquire() 获取一个许可，使用 release() 释放一个许可
     * 当许可数分配完之后，再使用acquire()会使线程进入阻塞，其他线程想要获取许可，必须等到有线程释放许可
     */
    public static void testSemaphore() {
        final Semaphore semaphore = new Semaphore(2);
        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "start...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "end...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "start...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "end...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "start...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "end...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static void testCAS() {

        AtomicInteger atomicInteger = new AtomicInteger();

    }


}
