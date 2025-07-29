package thesis.Operations;

public class Operation<K, V> {
    public final String tId;    //thread ID
    public final String opType; //operation type (put, get, remove)
    public final K key; 
    public final V input;       //input value if put, null if get/remove
    public final V output;      //return value from the map
    public final long timestamp;

    public Operation(String tId, String opType, K key, V input, V output, long timestamp) {
        this.tId = tId;
        this.opType = opType;
        this.key = key;
        this.input = input;
        this.output = output;
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return tId + ": " + opType + "(" + key + ", " + input + ") => " + output;
    }

}
