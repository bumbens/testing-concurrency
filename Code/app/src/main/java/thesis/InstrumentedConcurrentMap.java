package thesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InstrumentedConcurrentMap<K, V> {
    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();
    private final List<Operation<K, V>> history = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong clock = new AtomicLong();

    public V put(K key, V value){
        V result = map.put(key, value);
        record("get", key, value, result);
        return result;
    }

    public V get(K key){
        V result = map.get(key);
        record("get", key, null, result);
        return result;
    }

    public V remove(K key){
        V result = map.remove(key);
        record("remove", key, null, result);
        return result;
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
