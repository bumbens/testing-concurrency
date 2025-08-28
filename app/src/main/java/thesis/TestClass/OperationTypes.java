package thesis.TestClass;

public class OperationTypes<T> {
    public enum Type {
        ADD,
        REMOVE, 
        SNAPSHOT,
        ADDATINDEX
    }

    public final Type type;
    public final int index;
    public final T value;



    public OperationTypes(Type type, T value, int index) {
        
        this.type = type;
        this.value = value;
        this.index = index; // Only used for addAtIndex

    }

    public OperationTypes(Type type, T value) {
        this(type, value, -1);
    }

    public int getIndex() {
        return index;
    }

    public static <T> OperationTypes<T> add(T value) {
        return new OperationTypes<T>(Type.ADD, value);
    }

    public static <T> OperationTypes<T> remove(T value) {
        return new OperationTypes<T>(Type.REMOVE, value);
    }

    public static <T> OperationTypes<T> addAtIndex(int index, T value) {
        return new OperationTypes<T>(Type.ADDATINDEX, value, index) {
        };
    }

    public static <T> OperationTypes<T> snapshot(){
        return new OperationTypes<T>(Type.SNAPSHOT, null);
    }

}
