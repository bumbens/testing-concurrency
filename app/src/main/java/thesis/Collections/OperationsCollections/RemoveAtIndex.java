package thesis.Collections.OperationsCollections;

import java.util.List;

public class RemoveAtIndex<T> implements Interface_Collections<List<T>> {
    
    private final int index;
    public RemoveAtIndex(int index){
        this.index = index;
    }

    @Override
    public void run(List<T> list) {
        list.remove(index);
    }
    
}
