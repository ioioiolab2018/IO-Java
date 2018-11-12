package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.*;

import java.util.*;

public class DFS {
    private static Result connectionPath;
    private static Result result;
    private static Map<Integer, Node> nodeMap;

    public static List<Connection> run(Map<Integer, Node> nodes) {
        connectionPath = new Result();
        nodeMap = nodes;
        result = new Result();
        result.setValue(Float.MAX_VALUE);

        dfs(Data.getEnterNode(nodes));

        return result.getResultList();
    }

    private static void dfs(Node node) {
        if (node.getNodeType().equals(NodeType.EXIT)) {
            if (connectionPath.getValue() < result.getValue()) {
                result = new Result(connectionPath);
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
