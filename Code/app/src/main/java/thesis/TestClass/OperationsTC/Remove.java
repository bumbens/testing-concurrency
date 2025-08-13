package thesis.TestClass.OperationsTC;

import thesis.TestClass.TestClass;

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
