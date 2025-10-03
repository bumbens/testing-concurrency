package thesis.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import thesis.ValidPermutations;

public class Collections_Main {

    public <T> String testPermutations(){

        Set<ArrayList<String>> Expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"),
                Operations.add("v2"),
                Operations.remove("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

        return Expected.toString();
    }

    public static void main(String[] args) {
        Collections_Main testClassMain = new Collections_Main();

        List<String> test = new ArrayList<>();

        System.out.println(testClassMain.testPermutations());
    }
}
