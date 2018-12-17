package pl.put.poznan.analyzer.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = new Connection(0, 1, 1);
    }

    @Test
    void isEqualIdenticalConnectionsTest() {
        assertTrue(connection.isEqual(new Connection(0, 1, 1)));
    }

    @Test
    void isEqualTest() {
        assertTrue(connection.isEqual(new Connection(0, 1, -1)));
        assertTrue(connection.isEqual(new Connection(0, 1, 0)));
        assertTrue(connection.isEqual(new Connection(0, 1, 1)));
    }

    @Test
    void isNotEqualTest() {
        assertFalse(connection.isEqual(new Connection(1, 0, 1)));
        assertFalse(connection.isEqual(new Connection(0, 2, 1)));
        assertFalse(connection.isEqual(new Connection()));
    }

    @Test
    void isEqualWrongObjectTest() {
        assertFalse(connection.isEqual(null));
        assertFalse(connection.isEqual(new Object()));
    }

}