package thesis.TestClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ValidPermutations<T> {

    public static <T> Set<TestClass<T>> permutations(List<OperationTypes<T>> actors){
        if(actors.isEmpty()) {
            return Set.of(new TestClass<>());
        }

        // Total number of operations to perform
        int totalOperations = actors.size();
        // Initialize a set to hold unique outcomes
        // Use LinkedHashSet to maintain insertion order
        // and ensure uniqueness
        Set<TestClass<T>> outcomes = new LinkedHashSet<>();
        List<List<Integer>> permutations = new ArrayList<>();

        
        List<Integer> opId = new ArrayList<>(actors.size());
        for (int i = 0; i < actors.size(); i++) {
                opId.add(i);
        }

        // Calculate the number of permutations (factorial)
        // of the operations to explore all possible interleavings
        // e.g., for 3 operations, there are 3! = 6 permutations
        int attempts = factorial(totalOperations);
        

        // Perform the operations in all possible orders
        for(int op = 0; op < attempts; op++) {
                
                
                // Shuffle the operation indices to get a new order
                do  {
                        Collections.shuffle(opId);
                } while (permutations.contains(opId));
                permutations.add(new ArrayList<>(opId));

                // Create a new TestClass instance to represent the current state
                TestClass<T> currentState = new TestClass<>();

                // Execute operations in the shuffled order
                for (int j = 0; j<actors.size(); j++) {
                
                
                // DETERMINISTIC, not "random" - valid outcomes
                // State machine - algorithms for it?

                // Get the operation corresponding to the current index
                OperationTypes<T> operations = actors.get(opId.get(j));
                
                boolean valid = true;
                // Perform the operation on the current state of TestClass
                if(operations.type == OperationTypes.Type.ADD ) {
                        currentState.add(operations.value);
                } else if(operations.type == OperationTypes.Type.REMOVE) {
                        currentState.remove(operations.value);
                } else if(operations.type == OperationTypes.Type.SNAPSHOT) {
                        outcomes.add(new TestClass<>(currentState));
                } else if (operations.type == OperationTypes.Type.ADDATINDEX) {
                        int index = operations.getIndex();
                        int size = currentState.size();
                        if (index < 0 || index > size) { 
                                valid = false;
                        }
                        else {
                                currentState.add(index, operations.value);
                        }
                }
                }
                        
        }
        
        return outcomes;
    }
        

    

    private static int factorial(int actorsSize) {
        if (actorsSize == 0 || actorsSize == 1) {
            return 1;
        }
        return actorsSize * factorial(actorsSize - 1);
    }
        
    
}
    


