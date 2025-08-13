package thesis.TestClass;

public class OperationTypes<T> {
    public enum Type {
        ADD,
        REMOVE, 
        SNAPSHOT
    }

    public final Type type;
    public final T value;


    public OperationTypes(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    public static <T> OperationTypes<T> add(T value) {
        return new OperationTypes<T>(Type.ADD, value);
    }

    public static <T> OperationTypes<T> remove(T value) {
        return new OperationTypes<T>(Type.REMOVE, value);
    }

    public static <T> OperationTypes<T> snapshot(){
        return new OperationTypes<T>(Type.SNAPSHOT, null);
    }

}
