package thesis.TestClass;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class VPNew<T> {

    public static <T> Set<TestClass<T>> permutations(List<OperationTypes<T>> actors){
        Set<TestClass<T>> output = new LinkedHashSet<>();
        int actorsSize = actors.size();
        if (actors.isEmpty()) {
            output.add(new TestClass<>());
            return output;
        }
        boolean[] visited = new boolean[actorsSize];
        validPermutations(actors, visited, new TestClass<>(), 0, output);
        return output;
    }

    private static <T> void validPermutations(List<OperationTypes<T>> actors, boolean[] visited, TestClass<T> state, int depth, Set<TestClass<T>> output){
        if(depth == actors.size()) return;

        for (int i = 0; i < actors.size(); i++) {
            if (visited[i]) continue;

            OperationTypes<T> operation = actors.get(i);

            TestClass<T> currentState = new TestClass<>(state);

            if(operation.type == OperationTypes.Type.ADDATINDEX){
                int index = operation.getIndex();
                int size = currentState.size();
                if(index < 0 || index > size) continue;
            }

            switch(operation.type){
                case ADD:
                    currentState.add(operation.value);
                    break;
                case ADDATINDEX:
                    currentState.add(operation.getIndex(), operation.value);
                    break;
                case REMOVE:
                    currentState.remove(operation.value);
                    break;
                case SNAPSHOT:
                    output.add(new TestClass<>(currentState));
                    break;
                
            }
            
                visited[i] = true;
                validPermutations(actors, visited, currentState, depth + 1, output);
                visited[i] = false;
                }
        }
}





