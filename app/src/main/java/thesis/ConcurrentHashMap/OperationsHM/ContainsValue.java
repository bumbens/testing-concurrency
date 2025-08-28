package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class ContainsValue<K, V> implements MapOperation<K, V> {
    private final V value;

    public ContainsValue(V value) {
        this.value = value;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        boolean contains = map.containsValue(value);
        // For demonstration purposes:
        System.out.println("Does the map contain the value? " + contains);
    }
    
}
