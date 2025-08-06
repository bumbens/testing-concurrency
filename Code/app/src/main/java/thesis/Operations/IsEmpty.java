package thesis.Operations;

import thesis.ObservableConcurrentMap;

public class IsEmpty<K, V> implements MapOperation<K, V> {
    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        boolean isEmpty = map.isEmpty();
        // For demonstration purposes:
        System.out.println("Is the map empty? " + isEmpty);
    }
    
}
