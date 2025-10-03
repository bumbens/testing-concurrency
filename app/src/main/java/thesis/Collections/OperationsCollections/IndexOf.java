package thesis.Collections.OperationsCollections;

import java.util.List;

public class IndexOf<T> implements Interface_Collections<List<T>> {
    
    private final T value;
    public IndexOf(T value){
        this.value = value;
    }

    @Override
    public void run(List<T> list) {
        list.indexOf(value);
    }
    
}
