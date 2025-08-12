package thesis.TestClass.Operations_TestClass;

import thesis.TestClass.TestClass;
import thesis.TestClass.TestClassOperation;

public class Add<T> implements TestClassOperation<T> {
    
    private final T value;
    public Add(T value){
        this.value = value;
    }

    @Override
    public void run(TestClass<T> list) {
        list.add(value);
    }
}
