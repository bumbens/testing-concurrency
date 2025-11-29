package thesis.CaseStudies;

import java.util.ArrayList;
import java.util.List;

public class IllustrativeExamples<T> {
    // Non-thread-safe ArrayList implementation (no synchronization)
    // Possible issues: lost updates, inconsistent snapshots
    // AIOOBE, data corruption
    public static class ArrayListIllustrative <T> {
        private final List<T> list = new ArrayList<>();
        public synchronized void add(T o){
            list.add(o);
        }
        public synchronized void remove(T o){
            list.remove(o);
        }

        public List<T> snapshot(){
            return new ArrayList<>(list);
        }
    }

    public static class ArrayListIllustrativeFixed <T> {
        private final List<T> list = new ArrayList<>();
        public synchronized void add(T o){
            list.add(o);
        }
        public synchronized void remove(T o){
            list.remove(o);
        }

        public List<T> snapshot(){
            return new ArrayList<>(list);
        }
    }
    
}
