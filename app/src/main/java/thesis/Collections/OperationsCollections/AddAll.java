package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class AddAll<T> implements Interface_Collections<Collection<T>> {

    private final Collection<? extends T> values;

    public AddAll(Collection<? extends T> values){
        this.values = values;
    }

    @Override
    public void run(Collection<T> list) {
        list.addAll(values);
    }
} 
