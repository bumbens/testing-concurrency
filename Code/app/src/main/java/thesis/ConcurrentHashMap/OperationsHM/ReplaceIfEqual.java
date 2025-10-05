package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class ReplaceIfEqual<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V oldValue;
    private final V newValue;

    public ReplaceIfEqual(K key, V oldValue, V newValue) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.replace(key, oldValue, newValue);
    }
    
}
