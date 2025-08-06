package thesis.Operations;

import thesis.ObservableConcurrentMap;

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
