package thesis.Collections.OperationsCollections;

import java.util.Deque;

public class Push<T> implements Interface_Collections<Deque<? super T>> {

    private final T value;

    public Push(T value) {
        this.value = value;
    }

    @Override
    public void run(Deque<? super T> deque) {
        deque.push(value);
    }
    
}
