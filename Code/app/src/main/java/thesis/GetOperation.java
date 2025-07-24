package thesis;

public class GetOperation<K, V> implements MapOperation<K, V> {
    private final K key;

    public GetOperation(K key) {
        this.key = key;
    }

    @Override
    public void run(InstrumentedConcurrentMap<K, V> map) {
        map.get(key);
    }
}