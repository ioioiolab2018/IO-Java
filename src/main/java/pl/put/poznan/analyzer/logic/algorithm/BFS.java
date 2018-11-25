package pl.put.poznan.analyzer.logic.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.*;

/**
 * This class is used to find the most profitable path from entry to exit using BFS algorithm.
 */
@Service
public class BFS implements Algorithm{
    private static final Logger logger = LoggerFactory.getLogger(BFS.class);

    /**
     * Find the most profitable path by using BFS algorithm.
     * <br> Method uses pseudo-not-weighted network (made from original network) where every connection value is
     * interpreted as 1, so the method searches for the shortest path.
     *
     * @param nodes network (map of nodes) in which you want to find the most profitable path
     * @return the most profitable path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found
     */
    public Result run(Map<Integer, Node> nodes) {

        if (nodes == null) {
            throw new IllegalStateException("Incorrect network");
        }

        Map<Integer, Boolean> visited = new HashMap<>();
        Queue<Node> Q = new LinkedList<>();
        // Mape visited zerujemy
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            int id = entry.getKey();
            visited.putIfAbsent(id, false);
        }

        List<Connection> pathConnection = new LinkedList<>();
        Node exit = null;
        Node entry = Data.getEnterNode(nodes); //wierzchołek startowy
        if (entry == null) {
            return null;
        }

        Q.add(entry);
        visited.replace(entry.getId(), true);
        Node v;
        int u;
        boolean found = false;

        while (!(Q.isEmpty())) {
            v = Q.poll();
            // Pobieramy z kolejki wierzchołek v
            if (v.getNodeType() == NodeType.EXIT) {// Sprawdzamy koniec ścieżki
                found = true;        // Zaznaczamy sukces
                exit = v;
                break;               // Przerywamy pętlę
            }
            // Przeglądamy sąsiadów wierzchołka v
            for (Connection con : v.getOutgoing()) {
                u = con.getTo();
                if (!visited.get(u)) {
                    pathConnection.add(con);
                    Q.add(Data.getNodeById(nodes, u));
                    visited.replace(u, true);
                }
            }
        }

        if (found) {
            Collections.reverse(pathConnection);
            ArrayList<Integer> resultNodesList = new ArrayList<>();
            resultNodesList.add(exit.getId());
            float sum = 0;
            Node currentNode = exit;
            for (Connection con : pathConnection) {
                if (con.getTo() == currentNode.getId()) {
                    currentNode = Data.getNodeById(nodes, con.getFrom());
                    if (currentNode.getId() >= 0) {
                        resultNodesList.add(currentNode.getId());
                        sum += con.getValue();
                    }
                }
            }
            Collections.reverse(resultNodesList);
            logger.debug("The operation of the algorithm has been completed");
            return new Result(sum, resultNodesList);
        }
        return null;
    }
}