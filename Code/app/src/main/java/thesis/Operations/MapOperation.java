package thesis.Operations;

import thesis.InstrumentedConcurrentMap;

public interface MapOperation<K,V> {
    void run(InstrumentedConcurrentMap<K,V> map);
}
