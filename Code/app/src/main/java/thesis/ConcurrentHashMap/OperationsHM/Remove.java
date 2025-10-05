package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class Remove<K, V> implements MapOperation<K, V> {
    private final K key;

    public Remove(K key) {
        this.key = key;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.remove(key);
    }
    
}
