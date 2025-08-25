package thesis.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestClassMain {

    public <T> String testPermutations(){

        final Set<TestClass<String>> Expected = ValidPermutations.permutations(
            List.of(
                OperationTypes.add("v1"),
                OperationTypes.add("v2"),
                OperationTypes.add("v3"),
                OperationTypes.remove("v2"),
                OperationTypes.snapshot()
            ));

        return Expected.toString();
    }

    public static void main(String[] args) {
        TestClassMain testClassMain = new TestClassMain();

        List<String> test = new ArrayList<>();

        System.out.println(testClassMain.testPermutations());
    }
}
