package thesis;

public class PutOperation<K, V> implements MapOperation<K, V> {
    private final K key;
    private final V value;
    public PutOperation(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run(InstrumentedConcurrentMap<K,V> map) {
        map.put(key, value);
    }
}
