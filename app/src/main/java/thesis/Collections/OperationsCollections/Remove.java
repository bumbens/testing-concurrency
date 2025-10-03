package thesis.Collections.OperationsCollections;

import java.util.Collection;

public class Remove<T> implements Interface_Collections<Collection<? super T>> {
    private final T value;

    public Remove(T value){
        this.value = value;
    }

    @Override
    public void run(Collection<? super T> list) {
        list.remove(value);
    }
}
