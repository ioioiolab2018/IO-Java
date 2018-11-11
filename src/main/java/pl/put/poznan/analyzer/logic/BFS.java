package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;

import java.util.*;

public class BFS {

    public static List<Connection> run(List<Node> nodesList) {
        List<Node> newList= new ArrayList<>();
        int nodesId = 0;
        for (Node node : nodesList) {
            Node newNode = new Node(node.getId(), node.getName(), node.getNodeType());
            newList.add(newNode);
            for (Connection con : node.getOutgoing()) {
                float realValue=con.getValue();
                int value = (int) Math.ceil(con.getValue());
                int id = con.getTo();
                if (value > 1) {
                    nodesId -= 1;
                    Node additional = new Node(nodesId, "Additional", NodeType.REGULAR);
                    newList.add(additional);
                    newNode.getOutgoing().add(new Connection(newNode.getId(),nodesId, realValue));
                    value -= 1;
                    if (value <= 1) {
                        additional.getOutgoing().add(new Connection(additional.getId(), id, (float) value));
                        break;
                    } else {
                        nodesId -= 1;
                        additional.getOutgoing().add(new Connection(additional.getId(), nodesId, (float) value));
                        while (true) {
                            Node last =  new Node(nodesId, "Additional", NodeType.REGULAR);
                            newList.add(last);
                            value -= 1;
                            if (value <= 1) {
                                last.getOutgoing().add(new Connection(last.getId(), id, (float) value));
                                break;
                            }else {
                            last.getOutgoing().add(new Connection(nodesId , nodesId-1, (float) 1));
                            nodesId -= 1;
                            }
                        }
                    }
                } else {
                    newNode.getOutgoing().add(new Connection(newNode.getId(), id, (float) value));
                }
            }
        }

        System.out.println(newList);

        Map<Integer, Node> nodes = Data.getNodesMap(newList);
        if (nodes == null) {
            System.out.println("blad null");
            return null;
        }

        Map<Integer, Boolean> visited = new HashMap<>();
        Queue<Node> Q = new PriorityQueue<>();
        // Mape visited zerujemy
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            int id = entry.getKey();
            visited.putIfAbsent(id, false);
        }
        // Tworzymy tablicę ścieżki
        List<Node> patchNode = new LinkedList<>();
        List<Connection> patchConnection = new LinkedList<>();

        Node entry = Data.getEnterNode(nodes); //wierzchołek startowy
        if (entry != null) {
            patchNode.add(entry);
        } else {
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
                break;               // Przerywamy pętlę
            }
            // Przeglądamy sąsiadów wierzchołka v
            for (Connection con : v.getOutgoing()) {
                u = con.getTo();
                if (!visited.get(u)) {
                    if(con.getFrom()>=0 || con.getTo()>=0) {
                        patchConnection.add(con);
                    }
                    patchNode.add(nodes.get(u));
                    Q.add(Data.getNodeById(nodes, u));
                    visited.replace(u, true);
                    break;
                }
            }
        }
        if (found) {
            List<Connection> result= new ArrayList<>();
            for (Connection con : patchConnection){
                if(con.getFrom()>=0) {
                    int i = patchConnection.indexOf(con);
                    Connection temp= patchConnection.get(i+1);
                    result.add(new Connection(con.getFrom(),temp.getTo(), con.getValue()));
                }
            }
            return  result;
        }
        return null;
    }
}