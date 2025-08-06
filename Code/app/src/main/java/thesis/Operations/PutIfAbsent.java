package thesis.Operations;

import thesis.ObservableConcurrentMap;

public class PutIfAbsent<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V value;

    public PutIfAbsent(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.putIfAbsent(key, value);
    }
}
