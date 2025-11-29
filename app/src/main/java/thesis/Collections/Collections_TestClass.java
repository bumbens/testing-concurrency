package thesis.Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import thesis.Collections.OperationsCollections.Operations_Collections;

// T - same type consistenly used
// ? - wildcard
public class Collections_TestClass<T> {
    private List<T> list = new ArrayList<>();
    private List<Operations_Collections<T>> history = Collections.synchronizedList(new ArrayList<>());
    private AtomicInteger opCounter = new AtomicInteger();

    public Collections_TestClass(){
        super();
    }

    public Collections_TestClass(Collections_TestClass<T> snapshot){
        this();
        synchronized(snapshot){
            this.list.addAll(snapshot.list);
        }
    } 

    private void record(String opType, T value, int sizeBefore, int sizeAfter) {
        history.add(new Operations_Collections<>(
            Thread.currentThread().getName(), 
            opType, value, sizeBefore, sizeAfter, opCounter.getAndIncrement()
            ));
    }
 
    // Getters
    public List<Operations_Collections<T>> getHistory(){
        return new ArrayList<>(history);
    }

    public  List<T> getSnapshot(){
        return new ArrayList<>(list);
    }

    // Basic operations
    // All operations record their action in the history log
    public T add(T value){
        int sizeBefore = list.size();
        list.add(value);
        int sizeAfter = list.size();
        record("add", value, sizeBefore, sizeAfter);
        return value;
    }

    public boolean remove(T value){
        int sizeBefore = list.size();
        boolean removed = list.remove((Object) value);
        int sizeAfter = list.size();
        record(removed ? "remove" : "removeFailed", value, sizeBefore, sizeAfter);
        return removed;
    }

    public T get(int index){
        int size = list.size();
        record("get", null, size, size);
        return list.get(index);
    }

    public T add(int index, T value) {
        int before = list.size();
        if (index < 0 || index > before) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " out of bounds for size " + before);
        }
        list.add(index, value);
        record("addAtIndex", value, before, list.size());
        return value;
    }

    public T addIfAbsent(T value){
        int sizeBefore = list.size();

        if (!list.contains(value)) {
            list.add(value);
            int sizeAfter = list.size();
            record("addIfAbsent", value, sizeBefore, sizeAfter);    
        } else {
            record("addIfAbsent", null, sizeBefore, sizeBefore);
        }
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
        Collections_TestClass<T> toCompare = (Collections_TestClass<T>) obj;
        // Check if both lists have equal content and order

        return Objects.equals(this.list, toCompare.list); 
        //From the Java documentation (equals):
        // In other words, two lists are defined to be equal 
        //if they contain the same elements in the same order.
    }

}
