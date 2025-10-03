package thesis.Collections.OperationsCollections;

import java.util.Queue;

public class Element implements Interface_Collections<Queue<?>> {

    @Override
    public void run(Queue<?> queue) {
        queue.element();
    }
    
}
