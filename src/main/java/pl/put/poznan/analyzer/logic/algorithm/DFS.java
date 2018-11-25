package pl.put.poznan.analyzer.logic.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.Map;

/**
 * This class is used to find the most profitable path from entry to exit using DFS algorithm.
 */
@Service
public class DFS implements Algorithm {
    private static final Logger logger = LoggerFactory.getLogger(DFS.class);

    /**
     * Current path
     */
    private TemporaryPath connectionPath;
    /**
     * Current best path between entry and exit
     */
    private TemporaryPath result;
    /**
     * Network as HashMap
     */
    private Map<Integer, Node> nodeMap;

    /**
     * Find the most profitable path by using DFS algorithm.
     * <br> Method uses recursion.
     *
     * @param nodes network (HashMap) in which you want to find the most profitable path
     * @return the most profitable path as temporary path (list of connections and path's value)
     * <br> or NULL if path can't be found
     */
    @Override
    public Result run(Map<Integer, Node> nodes) {
        connectionPath = new TemporaryPath();
        nodeMap = nodes;
        result = new TemporaryPath();
        result.setValue(Float.MAX_VALUE);

        dfs(Data.getEnterNode(nodes));
        logger.debug("The operation of the algorithm has been completed");

        return result.getResult();
    }

    /**
     * Recursive function for DFS
     *
     * @param node node from which you go deeper into network
     */
    private void dfs(Node node) {
        if (node.getNodeType().equals(NodeType.EXIT)) {
            if (connectionPath.getValue() < result.getValue()) {
                result = new TemporaryPath(connectionPath);
            }
            return;
        }

        for (Connection connection : node.getOutgoing()) {
            connectionPath.add(connection);
            dfs(nodeMap.get(connection.getTo()));
            connectionPath.remove(connection);
        }
    }
}
