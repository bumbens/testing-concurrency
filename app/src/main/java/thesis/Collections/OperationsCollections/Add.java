package thesis.Collections.OperationsCollections;

import java.util.Collection;


public class Add<T> implements Interface_Collections<Collection<T>> {
    
    private final T value;
    public Add(T value){
        this.value = value;
    }

    @Override
    public void run(Collection<T> list) {
        list.add(value);
    }
}
