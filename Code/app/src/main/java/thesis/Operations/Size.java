package thesis.Operations;

import thesis.ObservableConcurrentMap;

public class Size<K, V> implements MapOperation<K, V> {
    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        // This operation does not modify the map, therefore it does not need to record an operation.
        // However, it can be used to retrieve the size of the map.
        int size = map.size();
        // For demonstration purposes:
        System.out.println("Size of the map: " + size);
    }
    
}
