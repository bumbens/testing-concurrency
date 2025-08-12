package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public interface MapOperation<K,V> {

    void run(ObservableConcurrentMap<K, V> map);
}
