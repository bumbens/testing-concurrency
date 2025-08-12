package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thesis.TestClass.TestClass;
import thesis.TestClass.ToFileTC;
import thesis.TestClass.Operations_TestClass.Add;
import thesis.TestClass.Operations_TestClass.Remove;

public class TestClassTest {

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
        String removeValue = "test";
        Add<String> addOp = new Add<String>(addValue);
        Remove<String> removeOp = new Remove<String>(removeValue);
        addOp.run(map);
        removeOp.run(map);

        ToFileTC.saveToFile(map, "TestClassTestRemove.html");
        assertEquals(0, map.size());

    }
}
