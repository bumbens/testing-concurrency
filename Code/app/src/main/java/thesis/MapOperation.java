package thesis;

public interface MapOperation<K,V> {
    void run(InstrumentedConcurrentMap<K,V> map);
}
