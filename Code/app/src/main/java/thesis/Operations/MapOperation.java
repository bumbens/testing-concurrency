package thesis.Operations;

import thesis.ObservableConcurrentMap;

public interface MapOperation<K,V> {
    void run(ObservableConcurrentMap<K,V> map);
}
