package thesis.Collections.OperationsCollections;

import java.util.List;

public class AddAtIndex<T> implements Interface_Collections<List<? super T>> {
    
    private final T value;
    private final int index;

    public AddAtIndex(int index, T value){
        this.index = index;
        this.value = value;
    }

    @Override
    public void run(List<? super T> list) {
        list.add(index, value);
    }
}
