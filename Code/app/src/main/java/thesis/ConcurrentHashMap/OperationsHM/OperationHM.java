package thesis.ConcurrentHashMap.OperationsHM;

public class OperationHM<K, V> {
    public final String tId;    //thread ID
    public final String opType; //operation type (put, get, remove)
    public final K key; 
    public final V input;       //input value if put, null if get/remove
    public final V output;      //return value from the map
    public final int opCounter;

    public OperationHM(String tId, String opType, K key, V input, V output, int opCounter) {
        this.tId = tId;
        this.opType = opType;
        this.key = key;
        this.input = input;
        this.output = output;
        this.opCounter = opCounter;
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
