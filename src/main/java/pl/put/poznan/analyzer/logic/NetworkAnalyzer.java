package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;
import pl.put.poznan.analyzer.logic.algorithm.*;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.List;

/**
 * This class is a service for REST and is responsible for calling functions
 */
@Service
public class NetworkAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzer.class);
    /**
     * Instance of BFS class, which is used to find the best path in network using BFS algorithm
     */
    private final Algorithm bfs;
    /**
     * Instance of DFS class, which is used to find the best path in network using DFS algorithm
     */
    private final Algorithm dfs;

    private final NetworkRepository networkRepository;

    private final NetworkOperations networkOperations;

    private final PathFinder pathFinder;

    /**
     * Class constructor for spring
     *
     * @param bfs instance of BFS to be used in program
     * @param dfs instance of DFS to be used in program
     */
    @Autowired
    public NetworkAnalyzer(BFS bfs, DFS dfs, NetworkRepository networkRepository, NetworkOperations networkOperations, PathFinder pathFinder) {
        this.bfs = bfs;
        this.dfs = dfs;
        this.networkRepository = networkRepository;
        this.networkOperations = networkOperations;
        this.pathFinder = pathFinder;
    }

    /**
     * Find the most profitable path in the network by BFS algorithm
     *
     * @param nodeList network (list of nodes) in which you want to find the best path
     * @return the best path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found
     */
    public Result findTheBestPathByBFS(List<Node> nodeList) {
        pathFinder.setAlgorithm(bfs);
        return pathFinder.findPath(nodeList);
    }

    /**
     * Find the most profitable path in the network by DFS algorithm
     *
     * @param nodeList network (list of nodes) in which you want to find the best path
     * @return the best path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found
     */
    public Result findTheBestPathByDFS(List<Node> nodeList) {
        pathFinder.setAlgorithm(dfs);
        return pathFinder.findPath(nodeList);
    }

    public int saveNetworkOnDatabase(String nodes) {
        Network network = new Network(nodes);
        networkRepository.save(network);
        return network.getId();
    }

    public String getNetwork(int id) {
        Network network = networkRepository.findOne(id);
        if (network == null) {
            throw new IllegalArgumentException("Incorrect id");
        }
        return network.getJsonValue();
    }

    public void deleteNetworkFromDatabase(int id) {
        networkRepository.delete(id);
    }

    /**
     * Add Nodes and Connection between Nodes to network which is saved in the database
     *
     * @param id    Network identifier which is stored in the database
     * @param nodes List of Nodes and Connections to be added
     * @return Modified List of Nodes
     */
    public List<Node> addNodesToNetwork(int id, List<Node> nodes) {
        return networkOperations.addNodesToNetwork(id, nodes);
    }

    /**
     * Add Connections between Nodes to network which is saved in the database
     *
     * @param id          Network identifier which is stored in the database
     * @param connections List of Connections to be added to the Network
     * @return Modified Network
     */
    public List<Node> addConnectionsToNetwork(int id, List<Connection> connections) {
        return networkOperations.addConnectionsToNetwork(id, connections);
    }
}
