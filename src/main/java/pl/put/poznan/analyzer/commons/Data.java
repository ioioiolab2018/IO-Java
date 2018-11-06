package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;


public class Data {
    public static List<Connection> getConnections(List<Node> nodes) {
        List<Connection> connections = new ArrayList<>();
        for (Node node : nodes) {
            for (Connection con : node.getIncoming()) {
                if (connections.indexOf(con) == -1) {
                    connections.add(con);
                }
            }
            for (Connection con : node.getOutgoing()) {
                if (connections.indexOf(con) == -1) {
                    connections.add(con);
                }
            }
        }
        return connections;
    }

    public static Node getNodeById(List<Node> nodes, int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        throw (new IllegalStateException());
    }

    public static boolean checkNetwork(List<Node> nodes) {
        //check nodes id
        // Check exit and enter
        int entryCount = 0;
        int exitCount = 0;
        for (Node checkedNode : nodes) {
            if (checkedNode.getNodeType() == NodeType.ENTRY)
                entryCount += 1;
            if (checkedNode.getNodeType() == NodeType.EXIT)
                exitCount += 1;
            if (entryCount > 1 || exitCount > 1) {
                return false;
            }
            for (Node node : nodes) {
                if (!checkedNode.equals(node)) {
                    if (checkedNode.getId() == node.getId())
                        return false;
                }
            }
            //sprawdzanie poprawnosci id w connections
            for (Connection con : checkedNode.getIncoming()) {
                if (con.getTo() == checkedNode.getId()) {
                    return false;
                }
            }
            for (Connection con : checkedNode.getOutgoing()) {
                if (con.getFrom() == checkedNode.getId()) {
                    return false;
                }
            }
        }
        return true;
    }


}
