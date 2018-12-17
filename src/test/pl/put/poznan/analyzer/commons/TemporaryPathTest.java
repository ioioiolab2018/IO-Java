package pl.put.poznan.analyzer.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TemporaryPathTest {

    private TemporaryPath temporaryPath;

    @BeforeEach
    void setUp() {
        ArrayList<Connection> connectionList = new ArrayList<>();
        Connection c1 = new Connection(0,1,1);
        Connection c2 = new Connection(1,4,4);
        Connection c3 = new Connection(4,3,2);
        connectionList.add(c1);
        connectionList.add(c2);
        connectionList.add(c3);
        temporaryPath = new TemporaryPath(connectionList);

    }

    @Test
    void addTest() {
        ArrayList<Integer>nodes = new ArrayList<>();
        nodes.add(0);
        nodes.add(1);
        nodes.add(4);
        nodes.add(3);
        nodes.add(2);
        Result expected = new Result(9, nodes);

        temporaryPath.add(new Connection(3,2,2));
        Result result = temporaryPath.getResult();

        assertAll(() -> assertNotNull(result, "Result is null"),
                () -> assertEquals(expected.getValue(), result.getValue()),
                () -> assertEquals(expected.getNodes(), result.getNodes()));
    }

    @Test
    void removeTest() {
        ArrayList<Integer>nodes = new ArrayList<>();
        nodes.add(0);
        nodes.add(1);
        nodes.add(4);
        Result expected = new Result(5, nodes);

        temporaryPath.remove(new Connection(4,3,2));
        Result result = temporaryPath.getResult();

        assertAll(() -> assertNotNull(result, "Result is null"),
                () -> assertEquals(expected.getValue(), result.getValue()),
                () -> assertEquals(expected.getNodes(), result.getNodes()));
    }

    @Test
    void removeNotExistingConnectionTest() {
        ArrayList<Integer>nodes = new ArrayList<>();
        nodes.add(0);
        nodes.add(1);
        nodes.add(4);
        nodes.add(3);
        Result expected = new Result(7, nodes);
        Result result;

        temporaryPath.remove(null);
        result = temporaryPath.getResult();
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getNodes(), result.getNodes());

        temporaryPath.remove(new Connection(1,3,1));
        result = temporaryPath.getResult();
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getNodes(), result.getNodes());

        temporaryPath.remove(new Connection(0,1,2));
        result = temporaryPath.getResult();
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getNodes(), result.getNodes());
    }

    @Test
    void getResultTest() {
        ArrayList<Integer>nodes = new ArrayList<>();
        nodes.add(0);
        nodes.add(1);
        nodes.add(4);
        nodes.add(3);
        Result expected = new Result(7, nodes);
        Result result = temporaryPath.getResult();
        assertAll(() -> assertNotNull(result, "Result is null"),
                () -> assertEquals(expected.getValue(), result.getValue()),
                () -> assertEquals(expected.getNodes(), result.getNodes()));
    }
}