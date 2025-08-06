package thesis.Operations;

import java.util.Map;

import thesis.ObservableConcurrentMap;

public class PutAll<K, V> implements MapOperation<K, V> {
    private final Map<? extends K, ? extends V> m;

    public PutAll(Map<? extends K, ? extends V> m) {
        this.m = m;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.putAll(m);
    }
    
}
