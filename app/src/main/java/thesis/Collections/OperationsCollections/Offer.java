package thesis.Collections.OperationsCollections;

import java.util.Queue;

public class Offer<T> implements Interface_Collections<Queue<? super T>> {
    
    private final T value;
    public Offer(T value){
        this.value = value;
    }

    @Override
    public void run(Queue<? super T> queue) {
        queue.offer(value);
    }
    
}
