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

    Integer[] array = new Integer[100];
    Random random = new Random();
    volatile boolean check;

    public MergeSortJCStressTest(){

        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt(1000);
        }

        check = true;
    }

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

    @Arbiter
    public void arbiter(Z_Result r){
        if(!check) {
            r.r1 = false;
        } else {
            r.r1 = MergeSort.isSorted(array);
        }
    }

}
