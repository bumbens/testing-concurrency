package thesis.Examples.Counter;

public class Counter {
    private long counter = 0L;

    public void increment() { 
        counter++; 
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
        Counter counter = new Counter();
        try {
            counter.runThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
