package thesis.Operations;

import java.util.function.BiFunction;

import thesis.InstrumentedConcurrentMap;

public class Compute<K, V> implements MapOperation<K, V> {
    private final K key;
    private final BiFunction<? super K, ? super V, ? extends V> remappingFunction;

    public Compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        this.key = key;
        this.remappingFunction = remappingFunction;
    }


    @Override
    public void run(InstrumentedConcurrentMap<K, V> map) {
        map.compute(key, remappingFunction);
    }
    
}
