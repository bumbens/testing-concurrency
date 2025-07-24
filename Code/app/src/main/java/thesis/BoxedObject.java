package thesis;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public class BoxedObject implements Serializable{
    private final AtomicReference <BoxedObject> value = new AtomicReference<>();

    public void set(BoxedObject v){
        value.set(v);
    }

    public BoxedObject get(){
        return value.get();
    }

}
