package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.TestClass.OperationTypes;
import thesis.TestClass.TestClass;
import thesis.TestClass.ToFileTC;
import thesis.TestClass.ValidPermutations;
import thesis.TestClass.OperationsTC.Add;
import thesis.TestClass.OperationsTC.AddAtIndex;
import thesis.TestClass.OperationsTC.Remove;
import thesis.TestClass.OperationsTC.TestClassOperation;

public class TestClassTest {

    private TestClass<String> observed;
    private TestClass<String> map;
    String addValue = "test";
    String removeValue = "test";
    @BeforeEach

    public void setUp(){
        map = new TestClass<>();
    }

    @Test
    public void testAdd(){
        
        Add<String> addOp = new Add<String>(addValue);
        addOp.run(map);

        assertEquals(addValue, map.get(0));
        
    }

    @Test
    public void testRemove(){
        Add<String> addOp = new Add<String>(addValue);
        Remove<String> removeOp = new Remove<String>(removeValue);
        addOp.run(map);
        removeOp.run(map);

        ToFileTC.saveToFile(map, "TestClassTestRemove.html");
        assertEquals(0, map.size());

    }


    @RepeatedTest(10000)
    public void testPermutations() throws InterruptedException{
        String add1 = "v1";
        String remove = "v2";
        int iterations = 10;

        final TestClassOperation<String> add_v1 = new Add<String>(add1);
        final TestClassOperation<String> remove_v1 = new Remove<String>(remove);
                

        final Set<TestClass<String>> Expected = ValidPermutations.permutations(
            List.of(
                OperationTypes.add("v1"), 
                OperationTypes.remove("v2"),
                OperationTypes.snapshot()
            ));

            for (int i = 0; i<iterations; i++){
                
                map = new TestClass<>();

                Thread t1 = new Thread(() -> {
                    add_v1.run(map);
                
                });

                Thread t2 = new Thread(() -> {
                    remove_v1.run(map);
                    
                });



                t1.start(); t2.start(); 
                t1.join(); t2.join(); 

                
                        assertTrue(
                            Expected.contains(map),
                            () -> "Unexpected snapshot: " + map + " | expected any of " + Expected
                        );
                
            }


    }
}
