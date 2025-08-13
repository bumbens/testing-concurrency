package thesis.TestClass.OperationsTC;

public class OperationTC<T> {
    private final String tId; //thread ID
    private final String opType; //operation type
    private final T value;
    private final int sizeBefore;
    private final int sizeAfter;
    private final int opCounter;

    public OperationTC(String tId, String opType, T value, int sizeBefore, int sizeAfter, int opCounter) {
        this.tId = tId;
        this.opType = opType;
        this.value = value;
        this.sizeBefore = sizeBefore;
        this.sizeAfter = sizeAfter;
        this.opCounter = opCounter;
    }
    public String gettId() {
        return tId;
    }
    public String getOpType() {
        return opType;
    }
    public T getValue() {
        return value;
    }
    public int getSizeBefore() {
        return sizeBefore;
    }
    public int getSizeAfter() {
        return sizeAfter;
    }
    public int getOpCounter() {
        return opCounter;
    }
    
    @Override
    public String toString(){
        return tId + ": " + opType + ", " + value + ", " + sizeBefore + ", " + sizeAfter;
    }
}
