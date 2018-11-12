package pl.put.poznan.analyzer.logic;

import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.*;

@Service
public class BFS {

    public Result run(List<Node> nodesList) {

        Map<Integer, Node> nodes = Data.changeToUnweighted(nodesList);
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
            return new Result(sum, resultNodesList);
        }
        return null;
    }
}