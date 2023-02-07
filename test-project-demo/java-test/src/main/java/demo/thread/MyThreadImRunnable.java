package demo.thread;

public class MyThreadImRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(">>> MyThreadImRunnable.run...");
    }
}
