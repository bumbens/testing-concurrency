package thesis;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;

public class MergeSortTest {

    
    @Test
    public void testSortingArray(){
        Integer[] array = {1, 3, 2, 6, 4, 5};
        MergeSort.sort(array);
        assertTrue(MergeSort.isSorted(array));
    }

    @Test
    public void testSortingWithThreadsArray() throws InterruptedException{
        Integer[] array = new Integer[1000];
        Random random = new Random();
        ReentrantLock lock = new ReentrantLock();

        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt(10000);
        }

        Thread t1 = new Thread(() -> {
            //synchronized (lock) {
                MergeSort.sort(array);
            //}
        });

        Thread t2 = new Thread(() -> {
            //synchronized (lock) {
                MergeSort.sort(array);
            //};
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        for (Integer integer : array) {
            assertNotNull(integer);
        }

        assertTrue(MergeSort.isSorted(array));
        
    }
}
