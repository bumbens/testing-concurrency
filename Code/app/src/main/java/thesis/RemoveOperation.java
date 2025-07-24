package thesis;

public class RemoveOperation<K, V> implements MapOperation<K, V> {
    private final K key;

    public RemoveOperation(K key) {
        this.key = key;
    }

    @Override
    public void run(InstrumentedConcurrentMap<K, V> map) {
        map.remove(key);
    }
    
}
