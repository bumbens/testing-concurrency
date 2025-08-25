package thesis.TestClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import thesis.TestClass.OperationsTC.OperationTC;

// T - same type consistenly used
// ? - wildcard
public class TestClass<T> {
    private List<T> list = new ArrayList<>();
    private List<OperationTC<T>> history = Collections.synchronizedList(new ArrayList<>());
    private AtomicInteger opCounter = new AtomicInteger();

    public TestClass(){}

    public TestClass(TestClass<T> snapshot){
        this();
        synchronized(snapshot){
            this.list.addAll(snapshot.list);
        }
    }

    private void record(String opType, T value, int sizeBefore, int sizeAfter) {
        history.add(new OperationTC<>(
            Thread.currentThread().getName(), 
            opType, value, sizeBefore, sizeAfter, opCounter.getAndIncrement()
            ));
    }

    public List<OperationTC<T>> getHistory(){
        return new ArrayList<>(history);
    }

    public synchronized void add(T value){
        int sizeBefore = list.size();
        list.add(value);
        int sizeAfter = list.size();
        record("add", value, sizeBefore, sizeAfter);
    }

    public synchronized void remove(T value){
        int sizeBefore = list.size();
        list.remove(value);
        int sizeAfter = list.size();
        record("remove", value, sizeBefore, sizeAfter);

    }

    public synchronized T get(int index){
        int size = list.size();
        record("get", null, size, size);
        return list.get(index);
    }

    public synchronized T add(int index, T value) {
        int before = list.size();
        if (index < 0 || index > before) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " out of bounds for size " + before);
        }
        list.add(index, value);
        record("addAtIndex", value, before, list.size());
        return value;
    }


    public synchronized boolean contains(T value){
        return list.contains(value);
    }

    public int size(){
        return list.size();
    }

    @Override
    public String toString(){
        return list.toString();
    }

    @Override
    public synchronized int hashCode() {
        return list.hashCode();
    }

    @Override
    public synchronized boolean equals(Object obj){
        // Shortcut - if both equal return true 
        if(this == obj) return true;
        // Return false if null or not same class
        if(obj == null || getClass() != obj.getClass()) return false;

        // Cast the input object to TestClass<T> to access its internal list for comparison
        TestClass<T> toCompare = (TestClass<T>) obj;
        // Check if both lists have equal content and order

        return Objects.equals(this.list, toCompare.list); 
        //From the Java documentation (equals):
        // In other words, two lists are defined to be equal 
        //if they contain the same elements in the same order.
    }

}
