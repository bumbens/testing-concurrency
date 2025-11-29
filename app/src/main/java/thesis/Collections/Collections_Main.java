package thesis.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import thesis.Oracle;

public class Collections_Main {

    public <T> String testPermutations(){

        Set<ArrayList<String>> Expected = Oracle.sequences(
            List.of(
                Operations.add(0, 0,"v1"),
                Operations.add(0, 1,"v2"),
                Operations.add(1, 0, "v3"),
                Operations.snapshot(2, 0)
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
