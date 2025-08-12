package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class Get<K, V> implements MapOperation<K, V> {
    private final K key;

    public Get(K key) {
        this.key = key;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.get(key);
    }
}