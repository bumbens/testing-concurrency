package thesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;

import thesis.Operations.Operation;
public class InstrumentedConcurrentMap<K, V> {
    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();
    private final List<Operation<K, V>> history = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong clock = new AtomicLong();

    public V put(K key, V value){
        V oldValue = map.put(key, value);
        record("put", key, value, oldValue);
        return oldValue;
    }

    public V get(K key){
        V oldValue = map.get(key);
        record("get", key, null, oldValue);
        return oldValue;
    }

    public V remove(K key){
        V oldValue = map.remove(key);
        record("remove", key, null, oldValue);
        return oldValue;
    }

    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue = map.get(key);
        V newValue = map.compute(key, remappingFunction);
        record("compute", key, oldValue, newValue);
        return newValue;
    }

    public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
        V newValue = map.computeIfAbsent(key, mappingFunction);
        record("computeIfAbsent", key, null, newValue);
        return newValue;
    }



    public boolean containsKey(K key) {
        return map.containsKey(key);
    }


    private void record(String opType, K key, V input, V output) {
        history.add(new Operation<>(
            Thread.currentThread().getName(),
            opType, key, input, output, 
            clock.incrementAndGet()
        ));
    }

    public List<Operation<K, V>> getHistory(){
        return new ArrayList<>(history);
    }
}
