package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.*;

import java.util.*;

public class DFS {
    private static TemporaryPath connectionPath;
    private static TemporaryPath result;
    private static Map<Integer, Node> nodeMap;

    public static TemporaryPath run(Map<Integer, Node> nodes) {
        connectionPath = new TemporaryPath();
        nodeMap = nodes;
        result = new TemporaryPath();
        result.setValue(Float.MAX_VALUE);

        dfs(Data.getEnterNode(nodes));

        return result;
    }

    private static void dfs(Node node) {
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
