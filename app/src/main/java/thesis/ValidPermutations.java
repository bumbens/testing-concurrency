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



public class ValidPermutations<T> {

    public static <T, C extends Collection <T>> Set<C> permutations(List<Operations<T>> actors, Supplier<C> factory){
        Set<C> output = new LinkedHashSet<>();
        int actorsSize = actors.size();
        if (actors.isEmpty()) {
            output.add(factory.get());
            return output;
        }
        boolean[] visited = new boolean[actorsSize];
        validPermutations(actors, visited, factory.get(), 0, factory, output);
        return output;
    }

    private static <T, C extends Collection<T>> void validPermutations(List<Operations<T>> actors, boolean[] visited, C state, int depth, Supplier<C> factory, Set<C> output){
        if(depth == actors.size()) return;

        for (int i = 0; i < actors.size(); i++) {
            if (visited[i]) continue;

            Operations<T> operation = actors.get(i);

            C currentState = factory.get();
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
                case SNAPSHOT:
                    C snapshot = factory.get();
                    snapshot.addAll(currentState);
                    output.add(snapshot);
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
            
                visited[i] = true;
                validPermutations(actors, visited, currentState, depth + 1, factory, output);
                visited[i] = false;
                }
        }
}






