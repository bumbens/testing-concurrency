package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class RemoveIfEqual<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V value;

    public RemoveIfEqual(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        if (map.get(key).equals(value)) {
            map.remove(key);
        }
    }
    
}
