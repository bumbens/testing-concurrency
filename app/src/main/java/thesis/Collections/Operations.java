package thesis.Collections;

import java.util.Collection;


public class Operations<T> {
    public enum Type {
        
    /* Collection */
    ADD,
    ADDALL,
    REMOVE,     // remove(Object o)
    REMOVEALL,  // removeAll(Collection<?> c)
    CONTAINS,
    CONTAINSALL,
    CLEAR,
    ISEMPTY,
    SIZE,
    SNAPSHOT,
    ADDIFABSENT,

    /* List */
    ADD_AT_INDEX,
    ADDALL_AT_INDEX,
    GET,
    INDEXOF,
    LASTINDEXOF,
    REMOVE_AT_INDEX,  // remove(int index)
    SET_AT_INDEX,

    /* Queue / Deque */
    OFFER,            // Queue
    PEEK,             // Queue
    POLL,             // Queue
    ELEMENT,          // Queue
    REMOVE_HEAD,      // Queue.remove() — removes head

    ADD_FIRST,        // Deque
    ADD_LAST,         // Deque
    PUSH,             // Deque (stack API)
    POP               // Deque (stack API)
}

    public final Type type;
    public final T value;
    public final int index; 
    public final Collection<? extends T> values;
    public final int actorId;
    public final int step;



    public Operations(Type type, T value, int index, Collection<? extends T> values, int actorId, int step) {
        
        this.type = type;
        this.value = value;
        this.index = index; 
        this.values = values;
        this.actorId = actorId;
        this.step = step;

    }

    public int getIndex() {
        return index;
    }


// --- Collection ---
public static <T> Operations<T> add(int actorId, int step, T value)                                
    { return new Operations<>(Type.ADD, value, -1, null, actorId, step); }

public static <T> Operations<T> addAll(int actorId, int step, Collection<? extends T> values)      
    { return new Operations<>(Type.ADDALL, null, -1, values, actorId, step); }

public static <T> Operations<T> remove(int actorId, int step, T value)                             
    { return new Operations<>(Type.REMOVE, value, -1, null, actorId, step); }

public static <T> Operations<T> removeAll(int actorId, int step, Collection<? extends T> values)   
    { return new Operations<>(Type.REMOVEALL, null, -1, values, actorId, step); }

public static <T> Operations<T> contains(int actorId, int step, T value)                           
    { return new Operations<>(Type.CONTAINS, value, -1, null, actorId, step); }

public static <T> Operations<T> containsAll(int actorId, int step, Collection<? extends T> values) 
    { return new Operations<>(Type.CONTAINSALL, null, -1, values, actorId, step); }

public static <T> Operations<T> clear(int actorId, int step)                                     
    { return new Operations<>(Type.CLEAR, null, -1, null, actorId, step); }

public static <T> Operations<T> isEmpty(int actorId, int step)                                   
    { return new Operations<>(Type.ISEMPTY, null, -1, null, actorId, step); }

public static <T> Operations<T> size(int actorId, int step)                                      
    { return new Operations<>(Type.SIZE, null, -1, null, actorId, step); }

public static <T> Operations<T> snapshot(int actorId, int step)                                  
    { return new Operations<>(Type.SNAPSHOT, null, -1, null, actorId, step); }

public static <T> Operations<T> addIfAbsent(int actorId, int step, T value)                        
    { return new Operations<>(Type.ADDIFABSENT, value, -1, null, actorId, step); }


// --- List ---
public static <T> Operations<T> add(int actorId, int step, int index, T value)                             
    { return new Operations<>(Type.ADD_AT_INDEX, value, index, null, actorId, step); }

public static <T> Operations<T> addAll(int actorId, int step, int index, Collection<? extends T> values)   
    { return new Operations<>(Type.ADDALL_AT_INDEX, null, index, values, actorId, step); }

public static <T> Operations<T> get(int actorId, int step, int index)                                      
    { return new Operations<>(Type.GET, null, index, null, actorId, step); }

public static <T> Operations<T> indexOf(int actorId, int step, T value)                                    
    { return new Operations<>(Type.INDEXOF, value, -1, null, actorId, step); }

public static <T> Operations<T> lastIndexOf(int actorId, int step, T value)                                
    { return new Operations<>(Type.LASTINDEXOF, value, -1, null, actorId, step); }

public static <T> Operations<T> removeAt(int actorId, int step, int index)                                 
    { return new Operations<>(Type.REMOVE_AT_INDEX, null, index, null, actorId, step); }

public static <T> Operations<T> set(int actorId, int step, int index, T value)                             
    { return new Operations<>(Type.SET_AT_INDEX, value, index, null, actorId, step); }



// --- Queue (head/offer/peek/poll semantics) ---
public static <T> Operations<T> offer(int actorId, int step, T value)                       
    { return new Operations<>(Type.OFFER, value, -1, null, actorId, step); }

public static <T> Operations<T> peek(int actorId, int step)                               
    { return new Operations<>(Type.PEEK, null, -1, null, actorId, step); }

public static <T> Operations<T> poll(int actorId, int step)                               
    { return new Operations<>(Type.POLL, null, -1, null, actorId, step); }

public static <T> Operations<T> element(int actorId, int step)                            
    { return new Operations<>(Type.ELEMENT, null, -1, null, actorId, step); }

public static <T> Operations<T> removeHead(int actorId, int step)                         
    { return new Operations<>(Type.REMOVE_HEAD, null, -1, null, actorId, step); } // Queue.remove()



// --- Deque (double-ended / stack) ---
public static <T> Operations<T> addFirst(int actorId, int step, T value)                    
    { return new Operations<>(Type.ADD_FIRST, value, -1, null, actorId, step); }

public static <T> Operations<T> addLast(int actorId, int step, T value)                     
    { return new Operations<>(Type.ADD_LAST, value, -1, null, actorId, step); }

public static <T> Operations<T> push(int actorId, int step, T value)                        
    { return new Operations<>(Type.PUSH, value, -1, null, actorId, step); }

public static <T> Operations<T> pop(int actorId, int step)                                
    { return new Operations<>(Type.POP, null, -1, null, actorId, step); }
    
    
}
