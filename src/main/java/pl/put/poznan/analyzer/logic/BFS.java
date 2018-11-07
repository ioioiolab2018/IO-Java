package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;

import javax.swing.text.StyledEditorKit;
import java.util.*;

public class BFS {

    public static List<Connection> run(List<Node> nodesList) {

        Map<Integer, Node> nodes = Data.getNodesMap(nodesList);
        if (nodes==null){
            return  null;
        }

        Map<Integer, Boolean> visited=new HashMap<>();
        Queue<Node> Q = new PriorityQueue<>();
        // Mape visited zerujemy
        for(Map.Entry<Integer, Node> entry : nodes.entrySet()){
            int id=entry.getKey();
            visited.putIfAbsent(id, false);
        }
        // Tworzymy tablicę ścieżki
        List<Node> patchNode = new LinkedList<>();
        List<Connection> patchConnection = new LinkedList<>();

        Node vs = Data.getEnterNode(nodes); //wierzchołek startowy
        if (vs != null) {
            patchNode.add(vs);
        }else {
            return null;
        }

        Q.add(vs);
        visited.replace(vs.getId(),true);
        Node v;
        int u;
        boolean found = false;

        while(!(Q.isEmpty()) ) {
            v = Q.poll();
            // Pobieramy z kolejki wierzchołek v
            if(v.getNodeType()== NodeType.EXIT ) {// Sprawdzamy koniec ścieżki
                found = true;        // Zaznaczamy sukces
                break;               // Przerywamy pętlę
            }
            // Przeglądamy sąsiadów wierzchołka v
            for(Connection con: v.getOutgoing()) {
                u = con.getTo();
                if(!visited.get(u))
                {
                    patchConnection.add(con);
                    patchNode.add(nodes.get(u));
                    Q.add(Data.getNodeById(nodes, u));
                    visited.replace(u,true);
                    break;
                }
            }
        }
        if (found){
            return  patchConnection;//TODO
        }
        return null;
    }
}