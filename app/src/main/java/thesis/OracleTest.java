package thesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import thesis.Collections.Operations;

public class OracleTest {

    
    public static void main(String[] args) {

        final  Set<ArrayList<String>> expected = thesis.Oracle.sequences(
            List.of(
                Operations.add(0,0, "item1"),
                Operations.add(1, 0, "item2"),
                Operations.remove(2, 0, "item1")
            ), 
            ArrayList::new);


        System.out.println(expected);
    }
}
