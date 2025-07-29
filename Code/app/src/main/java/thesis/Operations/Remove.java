package thesis.Operations;

import thesis.InstrumentedConcurrentMap;

public class Remove<K, V> implements MapOperation<K, V> {
    private final K key;

    public Remove(K key) {
        this.key = key;
    }

    @Override
    public void run(InstrumentedConcurrentMap<K, V> map) {
        map.remove(key);
    }
    
}
