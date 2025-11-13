package thesis.CaseStudies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class BuggyExamples<T> {
    // Non-thread-safe ArrayList implementation (no synchronization)
    // Possible issues: lost updates, inconsistent snapshots
    // AIOOBE, data corruption
    public static class ArrayListBroken <T> {
        private final List<T> list = new ArrayList<>();
        public void add(T o){
            list.add(o);
        }
        public void remove(T o){
            list.remove(o);
        }

        public List<T> snapshot(){
            return new ArrayList<>(list);
        }
    }

    // Non-atomic increment
    // Leads to lost updates in concurrent scenarios
    // Possible issues: incorrect counts

    // NOT 'BROKEN (sequentially)'
    public static class CounterBroken {
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
    public static class MessagePassingBroken {
        private volatile Object val;
        volatile boolean flag;

        public void put(Object val){
            //happens - before
            this.val = val;
            flag = true;
            
        }

        public Object getObject(){
            return val;
        }

        public boolean getFlag(){
            return flag;
        }
    }

    // Broken cache implementation
    // (no synchronization)
    // Leads to multiple computations for the same key
    // Possible issues: performance degradation

    // Fix:
    // - synchronize get method (one thread has access a time - slower)
    // - use ConcurrentHashMap
    public static class CacheBroken {
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

    //FIX
    public static class AddIfAbsentBroken {
        private final List<String> list = new ArrayList<>();

        public void addIfAbsent(String value){
            if (!list.contains(value)){
                list.add(value);
            }
        }

        public void remove(String value){
            list.remove(value);
        }

        public Set<String> snapshot(){
            return new HashSet<>(list);
        }
    }

    // Broken CAS implementation
    // Leads to failed updates in concurrent scenarios
    // Possible issues: ABA problem
    public static class CASBroken {
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
    
}
