package thesis.Collections.OperationsCollections;

import java.util.List;

public class LastIndexOf<T> implements Interface_Collections<List<T>> {
    
    private final T value;
    public LastIndexOf(T value){
        this.value = value;
    }

    @Override
    public void run(List<T> list) {
        list.lastIndexOf(value);
    }
    
}
