package thesis;
// For week 1
// raup@itu.dk * 2021-08-2

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterThreadsLock {
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
}
