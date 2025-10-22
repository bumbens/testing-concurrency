package thesis.HTML_Reports;

public interface HTMLRowMap<K, V> {
    String gettId();
    String getOpType();
    K getKey();
    V getOldValueMap();
    V getNewValueMap();
    int getSizeBefore();
    int getSizeAfter();
    int getOpCounter();
} 