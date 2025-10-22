package thesis.JUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import thesis.ValidPermutations;
import thesis.Collections.Operations;

public class OracleTest {

    @Test
    void snapshotTest() {
        Set<ArrayList<String>> expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"),
                Operations.add("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2"))));
        assertTrue(expected.contains(new ArrayList<>()));
    }

    @Test
    void addRemoveTest() {
        Set<ArrayList<String>> expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"),
                Operations.add("v2"),
                Operations.remove("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

        assertTrue(expected.contains(new ArrayList<>(List.of("v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v1"))));
        assertTrue(expected.contains(new ArrayList<>()));
    }

    @Test
    void addRemoveMultipleTest() {
        Set<ArrayList<String>> expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"),
                Operations.add("v2"),
                Operations.add("v3"),
                Operations.remove("v3"),
                Operations.remove("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

        assertTrue(expected.contains(new ArrayList<>(List.of("v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v3"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v3"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v3", "v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v3"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v3", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v2", "v3"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v1", "v3", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v1", "v3"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v2", "v3", "v1"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v3", "v1", "v2"))));
        assertTrue(expected.contains(new ArrayList<>(List.of("v3", "v2", "v1"))));
        assertTrue(expected.contains(new ArrayList<>()));   
    }
}
