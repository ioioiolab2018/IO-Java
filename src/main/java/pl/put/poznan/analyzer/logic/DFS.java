package pl.put.poznan.analyzer.logic;

import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.*;

@Service
public class DFS {
    private TemporaryPath connectionPath;
    private TemporaryPath result;
    private Map<Integer, Node> nodeMap;

    public TemporaryPath run(Map<Integer, Node> nodes) {
        connectionPath = new TemporaryPath();
        nodeMap = nodes;
        result = new TemporaryPath();
        result.setValue(Float.MAX_VALUE);

        dfs(Data.getEnterNode(nodes));

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
