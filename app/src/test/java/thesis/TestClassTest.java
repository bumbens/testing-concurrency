package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.Collections.Collections_TestClass;
import thesis.Collections.Collections_ToFile;
import thesis.Collections.Operations;
import thesis.Collections.OperationsCollections.Add;
import thesis.Collections.OperationsCollections.AddAtIndex;
import thesis.Collections.OperationsCollections.Interface_Collections;
import thesis.Collections.OperationsCollections.Remove;

public class TestClassTest {

    private Collections_TestClass<String> observed;
    private Collections_TestClass<String> map;
    String addValue = "test";
    String removeValue = "test";
    @BeforeEach

    public void setUp(){
        map = new Collections_TestClass<String>();
    }

    
    @Test
    public void testAdd(){
        
        map.add(addValue);

        assertEquals(addValue, map.get(0));
        
    }

    @Test
    public void testRemove(){
        map.add(addValue);
        map.remove(removeValue);

        //ArrayList_ToFile.saveToFile(map, "TestClassTestRemove.html");
        assertEquals(0, map.size());

    }


    @RepeatedTest(10)
    public void testPermutations() throws InterruptedException{
        String add1 = "v1";
        String add2 = "v2";
        String remove = "v2";
        int iterations = 10;

        final Interface_Collections<String> add_v1 = new Add<String>(add1);
        final Interface_Collections<String> remove_v1 = new Remove<String>(remove);
                

        final Set<List<String>> Expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"), 
                Operations.add("v2"),
                Operations.remove("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

            for (int i = 0; i<iterations; i++){
                
                map = new Collections_TestClass<String>();

                Thread t1 = new Thread(() -> {
                    map.add(add1);
                
                });

                Thread t2 = new Thread(() -> {
                    map.remove(remove);
                    
                });

                Thread t3 = new Thread(() -> {
                    map.add(add2);
                });



                t1.start(); t2.start(); t3.start(); 
                t1.join(); t2.join(); t3.join();

                    
                }

                List<String> snapshot = map.getSnapshot();
                
                        assertTrue(
                            Expected.contains(snapshot),
                            () -> "Unexpected snapshot: " + map + " | expected any of " + Expected
                        );
                
            }


}

