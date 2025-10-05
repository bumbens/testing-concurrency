package thesis.TestClass.OperationsTC;

import thesis.TestClass.TestClass;

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
