package pl.put.poznan.analyzer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.analyzer.commons.Network;
import pl.put.poznan.analyzer.logic.algorithm.BFS;
import pl.put.poznan.analyzer.logic.algorithm.DFS;
import pl.put.poznan.analyzer.logic.algorithm.GreedyAlgorithm;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class NetworkAnalyzerTest {
    private NetworkAnalyzer networkAnalyzer;
    private BFS bfsMock;
    private DFS dfsMock;
    private NetworkOperations networkOperations;
    private GreedyAlgorithm greedyAlgorithmMock;
    private NetworkRepository networkRepositoryMock;
    private PathFinder pathFinderMock;

    @BeforeEach
    void setUp() {
        bfsMock = mock(BFS.class);
        dfsMock = mock(DFS.class);
        pathFinderMock = mock(PathFinder.class);
        greedyAlgorithmMock = mock(GreedyAlgorithm.class);
        networkRepositoryMock = mock(NetworkRepository.class);
        networkOperations = mock(NetworkOperations.class);
        networkAnalyzer = new NetworkAnalyzer(bfsMock, dfsMock, networkRepositoryMock, networkOperations,
                pathFinderMock, greedyAlgorithmMock);
    }

    @Test
    void findTheBestPathByBFS() {
        networkAnalyzer.findTheBestPathByBFS(any());
        verify(pathFinderMock, times(1)).setAlgorithm(bfsMock);
        verify(pathFinderMock, times(1)).findPath(any());
    }

    @Test
    void findTheBestPathByDFS() {
        networkAnalyzer.findTheBestPathByDFS(any());
        verify(pathFinderMock, times(1)).setAlgorithm(dfsMock);
        verify(pathFinderMock, times(1)).findPath(any());
    }

    @Test
    void findTheBestPathByGreedy() {
        networkAnalyzer.findTheBestPathByGreedy(any());
        verify(pathFinderMock).setAlgorithm(greedyAlgorithmMock);
        verify(pathFinderMock).findPath(any());
    }

    @Test
    void saveNetworkOnDatabase() {
        String nodes = "{}";
        when(networkRepositoryMock.save(any(Network.class))).thenReturn(new Network());
        networkAnalyzer.saveNetworkOnDatabase(nodes);
        verify(networkRepositoryMock, times(1)).save(any(Network.class));
    }

    @Test
    void getNetwork() {
        int correctId = 10;
        int incorrectId = -1;
        Network networkMock = mock(Network.class);

        when(networkMock.getJsonValue()).thenReturn("json");
        when(networkRepositoryMock.findOne(correctId)).thenReturn(networkMock);
        when(networkRepositoryMock.findOne(incorrectId)).thenReturn(null);

        networkAnalyzer.getNetwork(correctId);

        verify(networkRepositoryMock).findOne(correctId);
        verify(networkMock).getJsonValue();

        assertAll(() -> assertEquals(networkAnalyzer.getNetwork(correctId), "json"),
                () -> assertThrows(IllegalArgumentException.class, () -> networkAnalyzer.getNetwork(incorrectId)));

    }

    @Test
    void deleteNetworkFromDatabase() {
        doNothing().when(networkRepositoryMock).delete(any(Network.class));
        networkAnalyzer.deleteNetworkFromDatabase(10);
        verify(networkRepositoryMock, times(1)).delete(10);
    }

    @Test
    void addConnectionsToNetwork() {
        // given
        when(networkOperations.addConnectionsToNetwork(any(), any())).thenReturn(new ArrayList<>());
        // when
        networkAnalyzer.addConnectionsToNetwork(1, new ArrayList<>());
        // then
        verify(networkOperations, times(1)).addConnectionsToNetwork(any(), any());
    }
}
