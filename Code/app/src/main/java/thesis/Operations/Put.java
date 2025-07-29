package thesis.Operations;

import thesis.InstrumentedConcurrentMap;

public class Put<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V value;
    public Put(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run(InstrumentedConcurrentMap<K,V> map) {
        map.put(key, value);
    }
}
