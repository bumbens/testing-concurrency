package thesis.ConcurrentHashMap.OperationsHM;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class Clear<K, V> implements MapOperation<K, V> {

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.clear();
    }
    
}
