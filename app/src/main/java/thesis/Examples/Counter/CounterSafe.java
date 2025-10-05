package thesis.Examples.Counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CounterSafe {
    private long counter = 0L;
    private final Lock lock = new ReentrantLock();

    public void increment() { 
        lock.lock();
        try {
        counter++; 
        } finally {
            lock.unlock();
        }
    }

    public long getCounter() { 
        return counter; 
    }
    
    public void runThreads() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 10_000; i++) {
            increment();
        }
    });

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 10_000; i++) {
            increment();
        }
    });

    t1.start(); t2.start();
    t1.join(); t2.join();

    System.out.println("Final counter value: " + getCounter());
    }

    public static void main(String[] args) {
        CounterSafe counter = new CounterSafe();
        try {
            counter.runThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
