package thesis.Collections.OperationsCollections;

import java.util.List;

public class Get<T> implements Interface_Collections<List<T>> {

    private final int index;

    public Get(int index){
        this.index = index;
    }

    @Override
    public void run(List<T> list) {
        list.get(index);
    }
    
}
