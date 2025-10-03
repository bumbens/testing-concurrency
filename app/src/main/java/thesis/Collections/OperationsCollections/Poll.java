package thesis.Collections.OperationsCollections;

import java.util.Queue;

public class Poll<T> implements Interface_Collections<Queue<?>> {

    @Override
    public void run(Queue<?> queue) {
        queue.poll();
    }
    
}
