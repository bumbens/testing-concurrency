package thesis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CounterTest {
    

    @Test
    public void testCounter() throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i=0; i<1000; i++)
            counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i=0; i<1000; i++)
            counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = 2000;
        int actual = counter.get();

        assertEquals(expected, actual);
    }

}
