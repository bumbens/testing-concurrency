package thesis.JCStress.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;
import thesis.ConcurrentHashMap.OperationsHM.Compute;
import thesis.ConcurrentHashMap.OperationsHM.Get;
import thesis.ConcurrentHashMap.OperationsHM.MapOperation;
import thesis.ConcurrentHashMap.OperationsHM.OperationHM;
import thesis.ConcurrentHashMap.OperationsHM.Put;
import thesis.ConcurrentHashMap.OperationsHM.Remove;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All reads consistent with some write")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Inconsistent read observed")
@State
public class JCStressTestCartesian {
    private final ObservableConcurrentMap<Object, Object> map = new ObservableConcurrentMap<>();
    private final Object key = "key";
    private final Object valuePut = "valuePut";
    private final Object valueCompute = "valueCompute";

    private final MapOperation<Object, Object> put = new Put<Object,Object>(key, valuePut);
    private final MapOperation<Object, Object> compute = new Compute<Object,Object>(key, (k, v) -> valueCompute);
    private final MapOperation<Object, Object> get = new Get<Object,Object>(key);
    private final MapOperation<Object, Object> remove = new Remove<Object,Object>(key);


    @Actor
    public void actor1(){
        put.run(map);
    }

    @Actor
    public void actor2(){
        compute.run(map);
    }

    @Actor
    public void actor3(){
        remove.run(map);
    }

    @Actor
    public void actor4(){
        get.run(map);
    }




    @Arbiter
    public void arbiter(Z_Result r) {
        List<OperationHM<Object, Object>> history = map.getHistory();
        
        List<OperationHM<Object, Object>> reads = new ArrayList<>();
        List<OperationHM<Object, Object>> writes = new ArrayList<>();

        for (OperationHM<Object,Object> operation : history) {

            String type = operation.getOpType();

            switch (type) {
                case "put":
                    writes.add(operation);
                    break;
                case "compute":
                    writes.add(operation);
                    break;
                case "remove":
                    writes.add(operation);
                    break;
                case "get":
                    reads.add(operation);
                    break;
                
            }
             
            }
        
        boolean consistent = true;
        for (OperationHM<Object, Object> read : reads) {
            boolean readConsistent = false;
            for (OperationHM<Object, Object> write : writes) {
                    if (Objects.equals(read.getOutput(), write.getOutput())){
                        readConsistent = true;
                        break;
                    }
                }

            if (!readConsistent) {
                consistent = false;
                break;
            }

        
        }
    
        r.r1 = consistent;
    }
}
