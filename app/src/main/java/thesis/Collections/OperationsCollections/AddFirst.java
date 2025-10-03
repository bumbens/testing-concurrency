package thesis.Collections.OperationsCollections;

import java.util.Deque;

public class AddFirst<T> implements Interface_Collections<Deque<? super T>> {

    private final T value;

    public AddFirst(T value) {
        this.value = value;
    }

    @Override
    public void run(Deque<? super T> deque) {
        deque.addFirst(value);
    }
    
}
