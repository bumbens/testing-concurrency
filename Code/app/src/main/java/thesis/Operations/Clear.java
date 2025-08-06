package thesis.Operations;

import thesis.ObservableConcurrentMap;

public class Clear<K, V> implements MapOperation<K, V> {
    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.clear();
    }
    
}
