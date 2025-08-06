package thesis;

public class MergeSort {

    private static Object[] aux;

    public static  <T extends Comparable<T>> void sort(T[] a) {
        aux = new Object[a.length];
        sort(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> boolean less(T v, T w){
        return v.compareTo(w) < 0;
    }

    public static <T extends Comparable<T>> void merge(T[] a, int lo, int mid, int hi){
        int i = lo;
        int j = mid+1;

        for (int k = lo; k <= hi; k++){
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++){
            if      (i > mid)                       a[k] = (T) aux[j++];
            else if (j > hi)                        a[k] = (T) aux[i++];
            else if (less((T) aux[j], (T) aux[i]))  a[k] = (T) aux[j++];
            else                                    a[k] = (T) aux[i++];
        }
    }
        
    public static <T extends Comparable<T>> boolean isSorted(T[] a){
        for(int i = 1; i<a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
        merge(a, lo, mid, hi);
    }
}