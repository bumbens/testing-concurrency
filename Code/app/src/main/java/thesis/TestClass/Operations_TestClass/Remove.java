package thesis.TestClass.Operations_TestClass;

import thesis.TestClass.TestClass;
import thesis.TestClass.TestClassOperation;

public class Remove<T> implements TestClassOperation<T> {
    private final T value;

    public Remove(T value){
        this.value = value;
    }

    @Override
    public void run(TestClass<T> list) {
        list.remove(value);
    }
}
