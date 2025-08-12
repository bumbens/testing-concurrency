package thesis.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestClass<T> {
    private final List<T> list;

    public TestClass(){
        this.list = new ArrayList<>();
    }

    public TestClass(TestClass<T> snapshot){
        this();
        synchronized(snapshot){
            this.list.addAll(snapshot.list);
        }
    }

    public void add(T value){
        list.add(value);
    }

    public void remove(T value){
        list.remove(value);
    }

    public boolean contains(T value){
        return list.contains(value);
    }

    public int size(){
        return list.size();
    }

    @Override
    public boolean equals(Object obj){
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
