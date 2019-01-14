package pl.put.poznan.analyzer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Network;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;
import pl.put.poznan.analyzer.converter.NodeListConverter;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class NetworkOperationsTest {
    private NodeListConverter nodeListConverter;
    private NetworkOperations networkOperations;
    private NetworkRepository networkRepository;

    @BeforeEach
    void setUp() {
        networkRepository = mock(NetworkRepository.class);
        nodeListConverter = mock(NodeListConverter.class);
        networkOperations = new NetworkOperations(networkRepository, nodeListConverter);
    }

    @Test
    void addNodesToNetwork() {
        // given
        List<Node> nodeList = Arrays.asList(
                new Node(1, "1", NodeType.ENTRY, Collections.singletonList(new Connection(1, 2, 1)), new ArrayList<>()),
                new Node(2, "2", NodeType.REGULAR, Collections.singletonList(new Connection(2, 3, 1)),
                        Collections.singletonList(new Connection(1, 2, 1))),
                new Node(3, "3", NodeType.EXIT, new ArrayList<>(), Collections.singletonList(new Connection(2, 3, 1))));
        when(nodeListConverter.convert(any())).thenReturn(nodeList);
        when(networkRepository.findOne(any())).thenReturn(new Network());

        // when
        List<Node> result = networkOperations.addNodesToNetwork(1, Collections.singletonList(
                new Node(4, "4", NodeType.REGULAR, Collections.singletonList(new Connection(4, 3, 1)),
                        Collections.singletonList(new Connection(2, 4, 1)))
        ));

        // then
        verify(networkRepository, times(1)).findOne(any());
        verify(nodeListConverter, times(1)).convert(any());
        assertAll(() -> assertEquals(4, result.size()));
    }

    @Test
    void addConnectionsToNetwork() {
    }

    @Test
    void deleteNodesFromNetwork() {
        // given
        List<Node> nodeList = Arrays.asList(
                new Node(1, "1", NodeType.ENTRY, Collections.singletonList(new Connection(1, 2, 1)), new ArrayList<>()),
                new Node(2, "2", NodeType.REGULAR, Collections.singletonList(new Connection(2, 3, 1)),
                        Collections.singletonList(new Connection(1, 2, 1))),
                new Node(4, "4", NodeType.REGULAR, Collections.singletonList(new Connection(4, 3, 1)),
                        Collections.singletonList(new Connection(2, 4, 1))),
                new Node(3, "3", NodeType.EXIT, new ArrayList<>(), Collections.singletonList(new Connection(2, 3, 1))));
        when(nodeListConverter.convert(any())).thenReturn(nodeList);
        when(networkRepository.findOne(any())).thenReturn(new Network());

        // when
        List<Node> result = networkOperations.deleteNodesFromNetwork(1, Collections.singletonList(
                new Node(4, "4", NodeType.REGULAR, Collections.singletonList(new Connection(4, 3, 1)),
                        Collections.singletonList(new Connection(2, 4, 1)))
        ));

        // then
        verify(networkRepository, times(1)).findOne(any());
        verify(nodeListConverter, times(1)).convert(any());
        assertAll(() -> assertEquals(3, result.size()));
    }

    @Test
    void deleteConnectionsFromNetwork() {
    }
}
