package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class Contains<T> implements Interface_Collections<Collection<T>> {

    private final T value;
    public Contains(T value){
        this.value = value;
    }

    @Override
    public void run(Collection<T> list) {
        list.contains(value);
    }
    
}
