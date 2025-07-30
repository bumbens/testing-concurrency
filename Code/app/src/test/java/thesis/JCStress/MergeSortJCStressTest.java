package thesis.JCStress;

import java.util.Random;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.MergeSort;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "Sorted")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class MergeSortJCStressTest {

    // Using a fixed size array for testing
    private final static int ARRAY_SIZE = 100;
    // Using a fixed range for random values
    private final static int MAX_RANDOM_VALUE = 1000;
    // Flag to check if the sorting was successful
    private volatile boolean check;

    // Array to be sorted
    Integer[] array = new Integer[ARRAY_SIZE];
    // Random number generator
    Random random = new Random();
    

    public MergeSortJCStressTest(){

        // Initializing the array with random values
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt(MAX_RANDOM_VALUE);
        }
        // Initially setting the check to true
        check = true;
    }

    // Actors that will run the sorting operation
    // Both actors will try to sort the same array concurrently
    // This is to test the thread-safety of the MergeSort implementation
    // If any actor throws a NullPointerException, it will set the check to false
    @Actor
    public void actor1(){
        try{
            MergeSort.sort(array);
        } catch (NullPointerException e) {
            check = false;
        }
    }

    @Actor
    public void actor2(){
        try{
            MergeSort.sort(array);
        } catch (NullPointerException e) {
            check = false;
        }
    }

    // Arbiter to check the final state of the array
    // It will check if the array is sorted and set the result accordingly
    // If the check is false, it will set the result to false
    // If the check is true, it will check if the array is sorted
    // If the array is sorted, it will set the result to true
    // If the array is not sorted, it will set the result to false
    @Arbiter
    public void arbiter(Z_Result r){
        if(!check) {
            r.r1 = false;
        } else {
            r.r1 = MergeSort.isSorted(array);
        }
    }

}
