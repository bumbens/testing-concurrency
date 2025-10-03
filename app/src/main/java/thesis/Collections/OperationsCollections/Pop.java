package thesis.Collections.OperationsCollections;

import java.util.Deque;

public class Pop<T> implements Interface_Collections<Deque<? extends T>> {

    @Override
    public void run(Deque<? extends T> deque) {
        deque.pop();
    }
    
}
