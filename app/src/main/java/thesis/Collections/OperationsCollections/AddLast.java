package thesis.Collections.OperationsCollections;

import java.util.Deque;

public class AddLast<T> implements Interface_Collections<Deque<? super T>> {

    private final T value;

    public AddLast(T value) {
        this.value = value;
    }

    @Override
    public void run(Deque<? super T> deque) {
        deque.addLast(value);
    }
    
}
