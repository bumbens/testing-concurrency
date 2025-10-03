package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class Clear<T> implements Interface_Collections<Collection<T>> {

    @Override
    public void run(Collection<T> list) {
        list.clear();
    }
    
}
