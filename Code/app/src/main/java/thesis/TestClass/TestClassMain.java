package thesis.TestClass;

import java.util.List;
import java.util.Set;

public class TestClassMain {

        public <T> String testPermutations(){
        String[] addList = {"v1", "v2"};
        String remove = "v1";

        final Set<TestClass<String>> Expected = ValidPermutations.permutations(
            List.of(
                List.of(OperationTypes.add("v1"), OperationTypes.add("v2")),
                List.of(OperationTypes.remove("v1")),
                List.of(OperationTypes.snapshot())
            ), 1);

        return Expected.toString();
    }

    public static void main(String[] args) {
        TestClassMain testClassMain = new TestClassMain();
        
        System.out.println(testClassMain.testPermutations());
    }
}
