package thesis;


import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;

import thesis.Collections.Operations;



public class Oracle<T> {
    // Use Supplier to create new instances of the collection
    // This allows for flexibility in the type of collection used
    // e.g., ArrayList, HashSet, LinkedList, etc.
    // The factory is a functional interface that provides a method to create new instances
    // of the specified collection type.
    public static <T, C extends Collection <T>> Set<C> sequences(List<Operations<T>> operations, Supplier<C> collFactory){
        Set<C> output = new LinkedHashSet<>();

        if (operations.isEmpty()) {
            output.add(collFactory.get());
            return output;
        }
        boolean[] actorVisited = new boolean[operations.size()];
        validPermutations(operations, actorVisited, collFactory.get(), 0, collFactory, output);
        return output;
    }

    private static <T> boolean canBeChecked(List<Operations<T>> operations, boolean[] actorVisited, int opIndex) {
        Operations<T> operation = operations.get(opIndex);
        int actorId = operation.actorId;
        int step = operation.step;

        if (step == 0) {
            return true; // First operation of the actor can always be executed
        }

        for (int i = 0; i < operations.size(); i++) {
            Operations<T> prevOperation = operations.get(i);
            if(prevOperation.actorId == actorId && prevOperation.step < step && !actorVisited[i]) {
                return false; // Previous operation of the same actor has not been executed yet
            }
        }
        return true;
    }

    private static <T, C extends Collection<T>> void validPermutations(List<Operations<T>> operations, boolean[] actorsVisited, C state, int depth, Supplier<C> collFactory, Set<C> output){
        if(depth == operations.size()) {
            C finalState = collFactory.get();
            finalState.addAll(state);
            output.add(finalState);
            return;
        }

        for (int i = 0; i < operations.size(); i++) {

            if (actorsVisited[i]) continue;
            if (!canBeChecked(operations, actorsVisited, i)) continue;
            
            Operations<T> operation = operations.get(i);

            C currentState = collFactory.get();
            currentState.addAll(state);

            switch(operation.type){

                /* Collection-level operations */
            
                case ADD:
                    currentState.add(operation.value);
                    break;
                case ADDALL:
                    currentState.addAll(operation.values);
                    break;
                case REMOVE:
                    currentState.remove(operation.value);
                    break;
                case REMOVEALL:
                    currentState.removeAll(operation.values);
                    break;
                case CONTAINS:
                    currentState.contains(operation.value);
                    break;
                case CONTAINSALL:
                    currentState.containsAll(operation.values);
                    break;
                case CLEAR:
                    currentState.clear();
                    break;
                case ISEMPTY:
                    currentState.isEmpty();
                    break;
                case SIZE:
                    currentState.size();
                    break;
                case ADDIFABSENT:
                    if(!currentState.contains(operation.value)){
                        currentState.add(operation.value);
                    }
                    break;
                    
                /* List-level operations */

                case ADD_AT_INDEX:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        int index = operation.index;
                        if (index >= 0 && index <= list.size()) {
                            list.add(index, operation.value);
                        }
                    }
                    break;
                case ADDALL_AT_INDEX:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        int index = operation.index;
                        if (index >= 0 && index <= list.size()) {
                            list.addAll(index, operation.values);
                        }
                    }
                    break;
                case GET:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        int index = operation.index;
                        if (index >= 0 && index < list.size()) {
                            list.get(index);
                        }
                    }
                    break;
                case INDEXOF:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        list.indexOf(operation.value);
                    }
                    break;
                case LASTINDEXOF:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        list.lastIndexOf(operation.value);
                    }
                    break;
                case REMOVE_AT_INDEX:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        int index = operation.index;
                        if (index >= 0 && index < list.size()) {
                            list.remove(index);
                        }
                    }
                    break;
                case SET_AT_INDEX:
                    if (currentState instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<T> list = (List<T>) currentState;
                        int index = operation.index;
                        if (index >= 0 && index < list.size()) {
                            list.set(index, operation.value);
                        }
                    }
                    break;

                /* Queue-level operations */

                case OFFER:
                    if (currentState instanceof Queue) {
                        @SuppressWarnings("unchecked")
                        Queue<T> queue = (Queue<T>) currentState;
                        queue.offer(operation.value);
                    } else if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.offer(operation.value);
                    } 
                    break;
                case PEEK:
                    if (currentState instanceof Queue) {
                        @SuppressWarnings("unchecked")
                        Queue<T> queue = (Queue<T>) currentState;
                        queue.peek();
                    } else if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.peek();
                    }
                    break;
                case POLL:
                    if (currentState instanceof Queue) {
                        @SuppressWarnings("unchecked")
                        Queue<T> queue = (Queue<T>) currentState;
                        queue.poll();
                    } else if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.poll();
                    }
                    break;
                case ELEMENT:
                    if (currentState instanceof Queue) {
                        @SuppressWarnings("unchecked")
                        Queue<T> queue = (Queue<T>) currentState;
                        try {
                            queue.element();
                        } catch (NoSuchElementException e) {}
                    }
                    break;
                case REMOVE_HEAD:
                    if (currentState instanceof Queue) {
                        @SuppressWarnings("unchecked")
                        Queue<T> queue = (Queue<T>) currentState;
                        try {
                            queue.remove();
                        } catch (NoSuchElementException e) {}
                    } 
                    break;
                case ADD_FIRST:
                    if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.addFirst(operation.value);
                    }
                    break;
                case ADD_LAST:
                    if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.addLast(operation.value);
                    }
                    break;
                case PUSH:
                    if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        deque.push(operation.value);
                    }
                    break;
                case POP:
                    if (currentState instanceof Deque) {
                        @SuppressWarnings("unchecked")
                        Deque<T> deque = (Deque<T>) currentState;
                        if (!deque.isEmpty()) {
                            deque.pop();
                        }
                    }
                    break;
                default:
                    break;
                
            }
            
                actorsVisited[i] = true;
                validPermutations(operations, actorsVisited, currentState, depth + 1, collFactory, output);
                actorsVisited[i] = false;
                }
        }
}






