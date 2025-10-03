package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class ContainsAll<T> implements Interface_Collections<Collection<T>> {

    private final Collection<T> values;
    public ContainsAll(Collection<T> values){
        this.values = values;
    }

    @Override
    public void run(Collection<T> list) {
        list.containsAll(values);
    }
    
}
