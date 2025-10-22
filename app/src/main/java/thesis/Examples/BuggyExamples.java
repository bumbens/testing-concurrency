package thesis.Examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// Non-thread-safe ArrayList implementation (no synchronization)
// Possible issues: lost updates, inconsistent snapshots
// AIOOBE, data corruption
class ArrayListBroken {
    private final List<Object> list = new ArrayList<>();
    public void add(Object o){
        list.add(o);
    }
    public void remove(Object o){
        list.remove(o);
    }

    public List<Object> snapshot(){
        return new ArrayList<>(list);
    }
}

// Non-atomic increment
// Leads to lost updates in concurrent scenarios
// Possible issues: incorrect counts
class CounterBroken {
    private int count = 0;

    public void increment(){
        count++;
    }

    public int getCount(){
        return count;
    }    
}

// Broken message passing implementation
// (no volatile or synchronization)
// Leads to visibility issues between threads
// Possible issues: stale reads, lost updates
class MessagePassingBroken {
    private Object val;

    public void put(Object o){
        val = o;
    }

    public Object get(){
        return val;
    }
}

// Broken cache implementation
// (no synchronization)
// Leads to multiple computations for the same key
// Possible issues: performance degradation
class CacheBroken {
    private final Map<String, Object> map = new HashMap<>();

    public Object get(String key){
        Object value = map.get(key);
        if (value == null){
            value = new Object(); // Simulate expensive computation
            map.put(key, value);
        }
        return value;
    }
}

// Broken addIfAbsent implementation
// (no synchronization)
// Leads to duplicate entries in concurrent scenarios
// Possible issues: data inconsistency, duplicates
class AddIfAbsentBroken {
    private final Set<String> set = new HashSet<>();

    public void addIfAbsent(String value){
        if (!set.contains(value)){
            set.add(value);
        }
    }

    public void remove(String value){
        set.remove(value);
    }

    public Set<String> snapshot(){
        return new HashSet<>(set);
    }
}

// Broken CAS implementation
// (improper use of AtomicReference)
// Leads to failed updates in concurrent scenarios
// Possible issues: ABA problem
class CASBroken {
    private final AtomicInteger atomicInt = new AtomicInteger(10);

    public boolean CASfrom10to5(){
        int current = atomicInt.get();
        if(current == 10){
            return atomicInt.compareAndSet(10, 5);
        }
        return false;
    }

    public void ABA(){
        atomicInt.set(0);
        atomicInt.set(10);
    }

    public int get(){
        return atomicInt.get();
    }
}