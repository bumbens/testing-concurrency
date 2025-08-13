package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thesis.TestClass.OperationTypes;
import thesis.TestClass.TestClass;
import thesis.TestClass.ToFileTC;
import thesis.TestClass.ValidPermutations;
import thesis.TestClass.OperationsTC.Add;
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


    @Test
    public void testPermutations() throws InterruptedException{
        String add1 = "v1";
        String add2 = "v2";
        String remove = "v1";
        int iterations = 10;

        final TestClassOperation<String> add_v1 = new Add<String>(add1);
        final TestClassOperation<String> add_v2 = new Add<String>(add2);
        final TestClassOperation<String> remove_v1 = new Remove<String>(remove);
                

        final Set<TestClass<String>> Expected = ValidPermutations.permutations(
            List.of(
                List.of(OperationTypes.add("v1"), OperationTypes.add("v2")),
                List.of(OperationTypes.remove("v1")),
                List.of(OperationTypes.snapshot())
            ), 100);

            for (int i = 0; i<iterations; i++){
                
                map = new TestClass<>();

                Thread t1 = new Thread(() -> {
                    add_v1.run(map);
                    add_v2.run(map);
                });

                Thread t2 = new Thread(() -> {
                    remove_v1.run(map);
                });

                Thread t3 = new Thread(() -> {
                    observed = new TestClass<>(map);
                });

                t1.start(); t2.start(); t3.start();
                t1.join(); t2.join(); t3.join();

                
                        assertTrue(
                            Expected.contains(observed),
                            () -> "Unexpected snapshot: " + observed + " | expected any of " + Expected
                        );
                
            }


    }
}
