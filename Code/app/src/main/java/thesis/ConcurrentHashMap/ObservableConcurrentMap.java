package thesis.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import thesis.ConcurrentHashMap.OperationsHM.OperationHM;

public class ObservableConcurrentMap<K, V> implements ConcurrentMap<K, V>{
    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();
    private final List<OperationHM<K, V>> history = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger opCounter = new AtomicInteger();

    private void record(String opType, K key, V input, V output) {
        history.add(new OperationHM<>(
            Thread.currentThread().getName(),
            opType, key, input, output, 
            opCounter.incrementAndGet()
        ));
    }

    public List<OperationHM<K, V>> getHistory(){
        return new ArrayList<>(history);
    }

    @Override
    public V put(K key, V value){
        V oldValue = map.put(key, value);
        record("put", key, oldValue, value);
        return oldValue;
    }
    @Override
    public V get(Object key){
        @SuppressWarnings("unchecked")
        K cast = (K) key;
        V oldValue = map.get(key);
        record("get", cast, null, oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key){
        @SuppressWarnings("unchecked")
        K cast = (K) key;
        V oldValue = map.remove(key);
        record("remove", cast, oldValue, null);
        return oldValue;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue = map.get(key);
        V newValue = map.compute(key, remappingFunction);
        record("compute", key, oldValue, newValue);
        return newValue;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
        V newValue = map.computeIfAbsent(key, mappingFunction);
        record("computeIfAbsent", key, null, newValue);
        return newValue;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> mappingFunction) {
        V oldValue = map.get(key);
        V newValue = map.computeIfPresent(key, mappingFunction);
        record("computeIfPresent", key, oldValue, newValue);
        return newValue;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V oldValue = map.putIfAbsent(key, value);
        record("putIfAbsent", key, oldValue, value);
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object key, Object value) {
        K cast = (K) key;
        V output;
        boolean result = map.remove(key, value);
        if(result) {
            output = (V) value;
        } else {
            output = null;
        }
        record("removeIfEqual", cast, output, (V) value);
        return result;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        K cast = (K) key;
        V output;
        boolean result = map.replace(key, oldValue, newValue);
        if(result) {
            output = newValue;
        } else {
            output = null;
        }
        // Record the operation
        record("replaceIfEqual", cast, oldValue, output);
        return result;
    }

    @Override
    public V replace(K key, V value) {
        K cast = (K) key;
        V oldValue = map.replace(key, value);
        record("replace", cast, oldValue, value);
        // Return the old value
        return oldValue;
    }

    @Override
    public void clear() {
        map.clear();
        record("clear", null, null, null);
    }

    @Override 
    public void putAll(Map<? extends K, ? extends V> m) { 
        map.putAll(m); 
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
