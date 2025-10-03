package thesis.Collections;

import java.util.ArrayList;
import java.util.List;

public class Collections_Example {
    List<String> list;

    public Collections_Example(){
        list = new ArrayList<>();
    }

    public synchronized void addIfAbsent(String item){
        if (!list.contains(item)) 
            list.add(item);
    }

    public synchronized void remove(String item){
        list.remove(item);
    }

    public synchronized List<String> getSnapshot(){
        return new ArrayList<>(list);
    }
    
    public String get(int index){
        return list.get(index);
    }

    public int size(){
        return list.size();
    }

    @Override
    public String toString(){
        return list.toString();
    }

    @Override 
    public int hashCode(){
        return list.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Collections_Example other = (Collections_Example) obj;
        return list.equals(other.list);
    }

}
