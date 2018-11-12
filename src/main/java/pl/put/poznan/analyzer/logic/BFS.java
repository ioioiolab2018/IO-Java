package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;

import java.util.*;

public class BFS {

    public static List<Connection> run(List<Node> nodesList) {
        List<Node> newList = new ArrayList<>();
        int nodesId = 0;
        for (Node node : nodesList) {
            Node newNode = new Node(node.getId(), node.getName(), node.getNodeType());
            newList.add(newNode);
            for (Connection con : node.getOutgoing()) {
                float realValue = con.getValue();
                int value = (int) Math.ceil(con.getValue());
                int id = con.getTo();
                if (value > 1) {
                    nodesId -= 1;
                    Node additional = new Node(nodesId, "Additional", NodeType.ADDITIONAL);
                    newList.add(additional);
                    newNode.getOutgoing().add(new Connection(newNode.getId(), nodesId, realValue));
                    value -= 1;
                    if (value <= 1) {
                        additional.getOutgoing().add(new Connection(additional.getId(), id, (float) value));
                    } else {
                        nodesId -= 1;
                        additional.getOutgoing().add(new Connection(additional.getId(), nodesId, (float) value));
                        while (true) {
                            Node last = new Node(nodesId, "Additional", NodeType.ADDITIONAL);
                            newList.add(last);
                            value -= 1;
                            if (value <= 1) {
                                last.getOutgoing().add(new Connection(last.getId(), id, (float) value));
                                break;
                            } else {
                                last.getOutgoing().add(new Connection(nodesId, nodesId - 1, (float) 1));
                                nodesId -= 1;
                            }
                        }
                    }
                } else {
                    newNode.getOutgoing().add(new Connection(newNode.getId(), id, (float) value));
                }
            }
        }


        Map<Integer, Node> network = Data.getNodesMap(nodesList);
        Map<Integer, Node> nodes = Data.getNodesMap(newList);
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
            List<Connection> resultPath = new ArrayList<>();
            Collections.reverse(pathConnection);
            Node currentNode = exit;
            Node varNode;
            Node pastNode = Data.getNodeById(network, exit.getId());
            for (Connection con : pathConnection) {
                if (con.getTo() == currentNode.getId()) {
                    currentNode = Data.getNodeById(nodes, con.getFrom());
                    if (currentNode.getId() >= 0) {
                        varNode = Data.getNodeById(network, con.getFrom());
                        for (Connection c : varNode.getOutgoing())
                            if (c.getTo() == pastNode.getId())
                                resultPath.add(c);
                        pastNode = currentNode;
                    }
                }
            }
            Collections.reverse(resultPath);
            return resultPath;
        }
        return null;
    }
}