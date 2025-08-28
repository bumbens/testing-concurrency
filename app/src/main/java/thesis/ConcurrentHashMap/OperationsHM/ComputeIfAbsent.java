package thesis.ConcurrentHashMap.OperationsHM;

import java.util.function.Function;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;

public class ComputeIfAbsent<K, V> implements MapOperation<K, V>{
    private final K key;
    private final Function<? super K,? extends V> mappingFunction;
    
    public ComputeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
        this.key = key;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public void run(ObservableConcurrentMap<K, V> map) {
        map.computeIfAbsent(key, mappingFunction);
    }
}
