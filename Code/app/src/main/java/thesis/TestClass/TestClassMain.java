package thesis.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestClassMain {

    public <T> String testPermutations(){

        final Set<TestClass<String>> Expected = VPNew.permutations(
            List.of(
                OperationTypes.add("v1"),
                OperationTypes.add("v2"),
                OperationTypes.add("v3"),
                OperationTypes.add("v4"),
                OperationTypes.add("v5"),
                OperationTypes.add("v6"),
                OperationTypes.add("v7"),
                OperationTypes.add("v8"),
                OperationTypes.add("v9"),
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
