package thesis.Collections.OperationsCollections;

import thesis.HTML_Reports.HTMLRow;

public class Operations_Collections<T> implements HTMLRow<T> {
    private final String tId; //thread ID
    private final String opType; //operation type
    private final T value;
    private final int sizeBefore;
    private final int sizeAfter;
    private final int opCounter;

    public Operations_Collections(String tId, String opType, T toAdd, int sizeBefore, int sizeAfter, int opCounter) {
        this.tId = tId;
        this.opType = opType;
        this.value = toAdd;
        this.sizeBefore = sizeBefore;
        this.sizeAfter = sizeAfter;
        this.opCounter = opCounter;
    }

    
    @Override
    public String gettId() {
        return tId;
    }
    @Override
    public String getOpType() {
        return opType;
    }
    @Override
    public T getValue() {
        return value;
    }
    @Override
    public int getSizeBefore() {
        return sizeBefore;
    }
    @Override
    public int getSizeAfter() {
        return sizeAfter;
    }
    @Override
    public int getOpCounter() {
        return opCounter;
    }



}
