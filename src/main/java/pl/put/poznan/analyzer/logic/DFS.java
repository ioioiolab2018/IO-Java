package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.*;

@Service
public class DFS {
    private static final Logger logger = LoggerFactory.getLogger(DFS.class);
    private TemporaryPath connectionPath;
    private TemporaryPath result;
    private Map<Integer, Node> nodeMap;

    TemporaryPath run(Map<Integer, Node> nodes) {
        connectionPath = new TemporaryPath();
        nodeMap = nodes;
        result = new TemporaryPath();
        result.setValue(Float.MAX_VALUE);

        dfs(Data.getEnterNode(nodes));
        logger.debug("The operation of the algorithm has been completed");

        return result;
    }

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
