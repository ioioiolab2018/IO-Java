package pl.put.poznan.analyzer.logic;

import javafx.scene.control.Tab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.analyzer.commons.Network;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.logic.algorithm.BFS;
import pl.put.poznan.analyzer.logic.algorithm.DFS;
import pl.put.poznan.analyzer.logic.algorithm.GreedyAlgorithm;
import pl.put.poznan.analyzer.repositories.NetworkRepository;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class NetworkAnalyzerTest {
    private NetworkAnalyzer networkAnalyzer;
    private BFS bfsMock;
    private DFS dfsMock;
    private NetworkOperations networkOperationsMock;
    private GreedyAlgorithm greedyAlgorithmMock;
    private NetworkRepository networkRepositoryMock;
    private PathFinder pathFinderMock;

    @BeforeEach
    void setUp() {
        bfsMock = mock(BFS.class);
        dfsMock = mock(DFS.class);
        networkOperationsMock = mock(NetworkOperations.class);
        pathFinderMock = mock(PathFinder.class);
        greedyAlgorithmMock = mock(GreedyAlgorithm.class);
        networkRepositoryMock = mock(NetworkRepository.class);
        networkAnalyzer=new  NetworkAnalyzer(bfsMock,  dfsMock, networkRepositoryMock, networkOperationsMock,  pathFinderMock, greedyAlgorithmMock);
    }

    @Test
    void findTheBestPathByBFS() {
        networkAnalyzer.findTheBestPathByBFS(anyList());
        verify(pathFinderMock).setAlgorithm(bfsMock);
        verify(pathFinderMock).findPath(anyList());
    }

    @Test
    void findTheBestPathByDFS() {
        networkAnalyzer.findTheBestPathByDFS(anyList());
        verify(pathFinderMock).setAlgorithm(dfsMock);
        verify(pathFinderMock).findPath(anyList());
    }

    @Test
    void findTheBestPathByGreedy() {
        networkAnalyzer.findTheBestPathByGreedy(anyList());
        verify(pathFinderMock).setAlgorithm(greedyAlgorithmMock);
        verify(pathFinderMock).findPath(anyList());
    }

    @Test
    void saveNetworkOnDatabase() {
        String nodes = "{}";
        assertEquals(0,networkAnalyzer.saveNetworkOnDatabase(nodes));
        verify(networkRepositoryMock).save(any(Network.class));

    }

    @Test
    void getNetwork() {
        int correctId =10;
        int incorrectId =-1;
        Network networkMock = mock(Network.class);

        when(networkMock.getJsonValue()).thenReturn("json");
        when(networkRepositoryMock.findOne(correctId)).thenReturn(networkMock);
        when(networkRepositoryMock.findOne(incorrectId)).thenReturn(null);

        networkAnalyzer.getNetwork(correctId);

        verify(networkRepositoryMock).findOne(correctId);
        verify(networkMock).getJsonValue();

        assertAll(() ->  assertEquals(networkAnalyzer.getNetwork(correctId), "json") ,
                () -> assertThrows(IllegalArgumentException.class, () -> networkAnalyzer.getNetwork(incorrectId)));

    }

    @Test
    void deleteNetworkFromDatabase() {
        networkAnalyzer.deleteNetworkFromDatabase(10);
        verify(networkRepositoryMock).delete(10);
    }

    @Test
    void addNodesToNetwork() {
    }

    @Test
    void addConnectionsToNetwork() {
    }

    @Test
    void deleteNodesFromNetwork() {
    }

    @Test
    void deleteConnectionsFromNetwork() {
    }
}