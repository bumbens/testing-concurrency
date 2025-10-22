package thesis.Collections.MapCollections;

import thesis.HTML_Reports.HTMLRowMap;

public class Operations_Map<K, V> implements HTMLRowMap<K, V> {
    private final String tId; //thread ID
    private final String opType; //operation type
    private final K key;
    private final V oldValueMap;
    private final V newValueMap;
    private final int sizeBefore;
    private final int sizeAfter;
    private final int opCounter;

    public Operations_Map(String tId, String opType, K key, V oldValueMap, V newValueMap, int sizeBefore, int sizeAfter, int opCounter) {
        this.tId = tId;
        this.opType = opType;
        this.key = key;
        this.oldValueMap = oldValueMap;
        this.newValueMap = newValueMap;
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
    public K getKey() {
        return key;
    }

    @Override
    public V getOldValueMap() {
        return oldValueMap;
    }

    @Override
    public V getNewValueMap() {
        return newValueMap;
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