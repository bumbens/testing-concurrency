package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class RemoveAll<T> implements Interface_Collections<Collection<? super T>> {

    private final Collection<T> values;

    public RemoveAll(Collection<T> values){
        this.values = values;
    }  

    @Override
    public void run(Collection<? super T> list) {
        list.removeAll(values);
    }
} 
