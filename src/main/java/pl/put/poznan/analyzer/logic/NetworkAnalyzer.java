package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;

import java.util.List;
import java.util.Map;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class NetworkAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzer.class);

    public NetworkAnalyzer() {
    }

    public static List<Connection> findTheBestPath(List<Node> nodeList, String mode) {
        Map <Integer, Node> nodesMap = Data.getNodesMap(nodeList);
        return mode.equals("BFS") ? BFS.run(nodeList) : DFS.run(nodesMap);
    }
}
