package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class Size<T> implements Interface_Collections<Collection<T>> {

    @Override
    public void run(Collection<T> list) {
        list.size();
    }
    
}
