package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class Replace<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V value;

    public Replace(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.replace(key, value);
    }
    
}
