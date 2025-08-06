package thesis.Operations;

public class Operation<K, V> {
    private final String tId;    //thread ID
    private final String opType; //operation type (put, get, remove)
    private final K key; 
    private final V input;       //input value if put, null if get/remove
    private final V output;      //return value from the map
    private final long opCounter;

    public Operation(String tId, String opType, K key, V input, V output, long timestamp) {
        this.tId = tId;
        this.opType = opType;
        this.key = key;
        this.input = input;
        this.output = output;
        this.opCounter = timestamp;
    }

    
    public String gettId() {
        return tId;
    }


    public String getOpType() {
        return opType;
    }


    public K getKey() {
        return key;
    }


    public V getInput() {
        return input;
    }


    public V getOutput() {
        return output;
    }


    public long getOpCounter() {
        return opCounter;
    }


    @Override
    public String toString(){
        return tId + ": " + opType + "(" + key + ", " + input + ") => " + output;
    }

}
