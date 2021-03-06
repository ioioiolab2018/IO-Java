package pl.put.poznan.analyzer.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private ArrayList<Node> nodeList;
    private Map<Integer, Node> nodeMap;

    @BeforeEach
    public void setUp() {
        Node node1 = new Node(0,
                "pariatur",
                NodeType.ENTRY,
                Collections.singletonList(
                        new Connection(0, 1, 2)),
                new ArrayList<>());
        Node node2 = new Node(1,
                "nonsensus",
                NodeType.REGULAR,
                Collections.singletonList(
                        new Connection(1, 2, 1)),
                Collections.singletonList(
                        new Connection(0, 1, 2)));
        Node node3 = new Node(2,
                "valhalla",
                NodeType.EXIT,
                new ArrayList<>(),
                Collections.singletonList(
                        new Connection(1, 2, 1))
        );

        nodeList = new ArrayList<>(Arrays.asList(node1, node2, node3));

        nodeMap = new HashMap<>();
        nodeMap.put(0, node1);
        nodeMap.put(1, node2);
        nodeMap.put(2, node3);
    }

    @Test
    void shouldReturnNodeMap() {
        // when
        Map<Integer, Node> nodeMap = Data.getNodesMap(nodeList);

        // then
        assertAll(() -> assertNotNull(nodeMap, "NodeMap is null"),
                () -> assertEquals(3, nodeMap.size()),
                () -> assertEquals(nodeList.get(0), nodeMap.get(0)),
                () -> assertEquals(nodeList.get(1), nodeMap.get(1)),
                () -> assertEquals(nodeList.get(2), nodeMap.get(2)));
    }

    @Test
    void shouldThrowExceptionWhenNodeRepeats() {
        // given
        nodeList.add(new Node(1,
                "nonsensus",
                NodeType.REGULAR,
                Collections.singletonList(
                        new Connection(1, 2, 1)),
                Collections.singletonList(
                        new Connection(0, 1, 2))));

        // then
        assertThrows(IllegalStateException.class, () -> Data.getNodesMap(nodeList), "A repeating vertex was found!");
    }

    @Test
    void shouldReturnNodeById() {
        // given
        Node node = new Node(1,
                "nonsensus",
                NodeType.REGULAR,
                Collections.singletonList(
                        new Connection(1, 2, 1)),
                Collections.singletonList(
                        new Connection(0, 1, 2)));

        // when
        Node result = Data.getNodeById(nodeMap, 1);

        // then
        assertAll(() -> assertNotNull(result, "Result is null"),
                () -> assertEquals(node, result));
    }

    @Test
    void shouldThrowWhenNodeNotExist() {
        // when
        nodeMap.remove(1);

        // then
        assertThrows(IllegalStateException.class, () -> Data.getNodeById(nodeMap, 1), "There is no node with id=1!");
    }

    @Test
    void shouldReturnEntryNode() {
        // given
        Node node = new Node(0,
                "pariatur",
                NodeType.ENTRY,
                Collections.singletonList(
                        new Connection(0, 1, 2)),
                new ArrayList<>());

        // when
        Node result = Data.getEnterNode(nodeMap);

        // then
        assertAll(() -> assertNotNull(result, "Result is null"),
                () -> assertEquals(node, result));
    }

    @Test
    void shouldThrowWhenEntryNodeNotExist() {
        // when
        nodeMap.remove(0);

        // then
        assertThrows(IllegalStateException.class, () -> Data.getEnterNode(nodeMap), "The entry node was not found!");
    }

    @Test
    void shouldNotThrowWhenNetworkIsGood() {
        // then
        assertDoesNotThrow(() -> Data.checkNetwork(nodeMap));
    }

    @Test
    void shouldThrowWhenExitNodeNotExist() {
        // given
        nodeMap.remove(2);

        // then
        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "The entry node was not found!");
    }

    @Test
    void shouldThrowWhenExitNodeQuantityIsTwo() {
        // given
        nodeMap.put(3, new Node(3,
                "valhalla",
                NodeType.EXIT,
                new ArrayList<>(),
                Collections.singletonList(
                        new Connection(1, 3, 1))
        ));
        ArrayList<Connection> connections = new ArrayList<>(nodeMap.get(1).getOutgoing());
        connections.add(new Connection(1, 3, 1));
        nodeMap.get(1).setOutgoing(connections);

        // then
        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "The entry node was not found!");
    }

    @Test
    void shouldThrowWhenNodeRepeats() {
        // given
        nodeList.add(new Node(2,
                "valhalla",
                NodeType.EXIT,
                new ArrayList<>(),
                Collections.singletonList(
                        new Connection(1, 2, 1))
        ));

        // then
        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "The entry node was not found!");
    }

    @Test
    void shouldThrowWhenConnectionRepeats() {
        // given
        ArrayList<Connection> connections = new ArrayList<>(nodeMap.get(1).getOutgoing());
        connections.add(new Connection(1, 2, 8));
        nodeMap.get(1).setOutgoing(connections);

        // then
        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "The entry node was not found!");
    }

    @Test
    void shouldThrowWhenPathNotExist() {
        nodeMap.remove(1);

        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "There is no path in network");
    }

    @Test
    void shouldThrowWhenValueIsNegative() {

        nodeMap.remove(1);
        Node node2 = new Node(1,
                "nonsensus",
                NodeType.REGULAR,
                Collections.singletonList(
                        new Connection(1, 2, -1)),
                Collections.singletonList(
                        new Connection(0, 1, 2)));
        nodeMap.put(1, node2);

        // then
        assertThrows(IllegalStateException.class, () -> Data.checkNetwork(nodeMap), "Value of the connection is less than 0");
    }

    @Test
    void changeToUnweighted() {
        // when
        Map<Integer, Node> result = Data.changeToUnweighted(nodeList);

        // then
        assertAll(() -> assertNotNull(result, "Result is null!"),
                () -> assertEquals(4, result.size()),
                () -> assertEquals(NodeType.ADDITIONAL, result.get(-1).getNodeType()));
    }
}