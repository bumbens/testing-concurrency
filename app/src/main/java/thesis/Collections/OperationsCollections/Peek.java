package thesis.Collections.OperationsCollections;

import java.util.Queue;

public class Peek<T> implements Interface_Collections<Queue<?>> {

    @Override
    public void run(Queue<?> queue) {
        queue.peek();
    }
    
}
