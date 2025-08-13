package thesis.TestClass;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ValidPermutations<T> {

    public static <T> Set<TestClass<T>> permutations(List<List<OperationTypes<T>>> actors, int iterations){
        if(actors.isEmpty()) {
            return Set.of(new TestClass<>());
        }
        // Initialize a set to hold unique outcomes
        // Use LinkedHashSet to maintain insertion order
        // and ensure uniqueness
        Set<TestClass<T>> outcomes = new LinkedHashSet<>();
        // Calculate the total number of operations across all actors
        
        Random random = new Random();

        //
        for(int i = 0; i < iterations; i++){
                // Initialize an array to keep track of the current operation index for each actor
                int[] opId = new int[actors.size()];

                // Create a new TestClass instance to represent the current state
                TestClass<T> currentState = new TestClass<>();

                // Loop through each actor's operations
                // and calculate the total number of operations
                // to be performed in this iteration
                int numberOfOperations = 0;
                for (List<OperationTypes<T>> actor : actors) {
                        
                        numberOfOperations += actor.size();
                }

                // Loop through the number of operations
                // and randomly select an actor to perform an operation
                // until all operations are performed
                for(int op = 0; op < numberOfOperations; op++) {
                        // Create a list to hold the indices of actors that still have operations to perform
                        List<Integer> toDo = new ArrayList<>();
                        // Populate the toDo list with actors that have remaining operations
                        for (int j = 0; j<actors.size(); j++) 
                                // Check if the current actor has more operations to perform
                                if(opId[j]<actors.get(j).size()) toDo.add(j);
                        if(toDo.isEmpty()) break;
                        
                        // Randomly select an actor from the toDo list
                        int actor = toDo.get(random.nextInt(toDo.size()));
                        // Get the operation for the selected actor
                        // and increment the operation index for that actor
                        OperationTypes<T> operations = actors.get(actor).get(opId[actor]++);

                        // Perform the operation on the current state of TestClass
                        if(operations.type == OperationTypes.Type.ADD) {
                                currentState.add(operations.value);
                        } else if(operations.type == OperationTypes.Type.REMOVE) {
                                currentState.remove(operations.value);
                        } else if(operations.type == OperationTypes.Type.SNAPSHOT) {
                                outcomes.add(new TestClass<>(currentState));
                        }
                        
                }
        }
        return outcomes;
    }
        

        
    
}
    


