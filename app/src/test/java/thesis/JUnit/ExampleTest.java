package thesis.JUnit;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.ValidPermutations;
import thesis.Collections.Collections_Example;
import thesis.Collections.Operations;

public class ExampleTest {
    
    private Collections_Example list;

    @BeforeEach
    public void setUp(){
        list = new Collections_Example();
    }

    @Test
    public void testAddIfAbsent(){
        String value = "test";
        list.addIfAbsent(value);
        
        assert(list.get(0).equals(value));
    }

    @Test
    public void testRemove(){
        String value = "test";
        list.addIfAbsent(value);
        list.remove(value);

        assert(list.size() == 0);
    }

    @RepeatedTest(10)
    public void threads() throws InterruptedException{
        String value1 = "v1";
        String value2 = "v2";
        int iterations = 10;

        final var Expected = ValidPermutations.permutations(
            List.of(
                Operations.remove("v1"),
                Operations.snapshot()
            ), 
            ArrayList::new);

            for (int i = 0; i < iterations; i++) {
                Thread t1 = new Thread(() -> {
            
                    list.addIfAbsent(value1);
            
                });

                Thread t2 = new Thread(() -> {
                    
                    list.remove(value1);
                });

                

                t1.start();
                t2.start();

                t1.join();
                t2.join();

            }

                List<String> snapshot = list.getSnapshot();
                assertTrue(
                            Expected.contains(snapshot),
                            () -> "Unexpected snapshot: " + list + " | expected any of " + Expected
                        );
            }
        
}
        

