package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.List;
import java.util.Map;

/**
 * This class is a service for REST and is responsible for calling functions
 */
@Service
public class NetworkAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzer.class);
    /**
     * Instance of BFS class, which is used to find the best path in network using BFS algorithm
     */
    private final BFS bfs;
    /**
     * Instance of DFS class, which is used to find the best path in network using DFS algorithm
     */
    private final DFS dfs;

    private final NetworkRepository networkRepository;

    private final NetworkOperations networkOperations;

    /**
     * Class constructor for spring
     *
     * @param bfs instance of BFS to be used in program
     * @param dfs instance of DFS to be used in program
     */
    @Autowired
    public NetworkAnalyzer(BFS bfs, DFS dfs, NetworkRepository networkRepository, NetworkOperations networkOperations) {
        this.bfs = bfs;
        this.dfs = dfs;
        this.networkRepository = networkRepository;
        this.networkOperations = networkOperations;
    }

    /**
     * Find the most profitable path in the network
     *
     * @param nodeList network (list of nodes) in which you want to find the best path
     * @param mode     name of algorithm you want to use for searching for the best path
     * @return the best path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found
     */
    public Result findTheBestPath(List<Node> nodeList, String mode) {
        if (!Data.checkNetwork(nodeList)) {
            logger.error("Incorrect network");
            throw new IllegalArgumentException("Incorrect network");
        }
        Map<Integer, Node> nodesMap = Data.getNodesMap(nodeList);
        logger.debug("Prepared to run the algorithm");
        return mode.equals("BFS") ? bfs.run(nodeList) : dfs.run(nodesMap).getResult();
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
