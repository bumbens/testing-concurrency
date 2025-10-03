package thesis.Collections.OperationsCollections;

import java.util.Collection;
import java.util.List;

public class AddAllAtIndex<T> implements Interface_Collections<List<T>> {
    
    private final int index;
    private final Collection<? extends T> values;

    public AddAllAtIndex(int index, Collection<? extends T> values) {
        this.index = index;
        this.values = values;
    }

    @Override
    public void run(List<T> list) {
        list.addAll(index, values);
    }
    
}
