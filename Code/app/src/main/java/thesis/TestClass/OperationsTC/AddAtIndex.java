package thesis.TestClass.OperationsTC;

import thesis.TestClass.TestClass;

public class AddAtIndex<T> implements TestClassOperation<T> {
    
    private final T value;
    private final int index;

    public AddAtIndex(int index, T value){
        this.index = index;
        this.value = value;
    }

    @Override
    public void run(TestClass<T> list) {
        list.add(index, value);
    }
}
