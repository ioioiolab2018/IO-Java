package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Data {
    /**
     * Change network format from list of nodes to hashMap
     * @param nodes network as the list of nodes
     * @return the network as hashMap
     */
    public static Map<Integer, Node> getNodesMap(List<Node> nodes){
        Map<Integer, Node> nodesMap = new HashMap<>();
        for (Node node: nodes) {
            if(nodesMap.putIfAbsent(node.getId(), node)!=null){
                return null;
            }
        }
        return nodesMap;
    }

    public static Node getNodeById(Map<Integer, Node> nodes, int id) {
        return nodes.get(id);
    }

    public static  Node getEnterNode(Map<Integer,Node> nodes){

        for(Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue();
            if(node.getNodeType()== NodeType.ENTRY){
                return node;
            }
        }
        return  null;
    }

    /**
     * Check validity of the network (hashMap)
     * @param nodes the entire network as hashMap
     * @return TRUE, when the network is valid
     *         FALSE, when the network is invalid
     */
    public static boolean checkNetwork(Map<Integer,Node> nodes) {
        // Check if there are only one exit and entry
        int entryCount = 0;
        int exitCount = 0;
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            Node checkedNode = entry.getValue();
            if (checkedNode.getNodeType() == NodeType.ENTRY)
                entryCount += 1;
            if (checkedNode.getNodeType() == NodeType.EXIT)
                exitCount += 1;
            if (entryCount > 1 || exitCount > 1) {
                return false;
            }
            // Check validity of id in incoming and outgoing connections
            for (Connection con : checkedNode.getIncoming()) {
                if (con.getTo() != checkedNode.getId()) {
                    return false;
                }
            }
            for (Connection con : checkedNode.getOutgoing()) {
                if (con.getFrom() != checkedNode.getId()) {
                    return false;
                }
            }
        }
        return true;
    }








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

    public static int getMaxId(List<Node> nodes){
        int max =0;
        for (Node node: nodes) {
            if (node.getId()>=max){
                max=node.getId();
            }
        }
        return max;
    }

    public static Node getNodeById(List<Node> nodes, int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        throw (new IllegalStateException());
    }

    public static  Node getEnterNode(List<Node> nodes){
        for (Node node:nodes) {
            if (node.getNodeType()== NodeType.ENTRY){
                return node;
            }
        }
        return  null;
    }

    /**
     * Check validity of the network (list of nodes)
     * @param nodes the entire network as list of nodes
     * @return TRUE, when the network is valid
     *         FALSE, when the network is invalid
     */
    public static boolean checkNetwork(List<Node> nodes) {
        //check nodes id
        // Check if there are only one exit and entry
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
            // Check validity of id in incoming and outgoing connections
            for (Connection con : checkedNode.getIncoming()) {
                if (con.getTo() != checkedNode.getId()) {
                    return false;
                }
            }
            for (Connection con : checkedNode.getOutgoing()) {
                if (con.getFrom() != checkedNode.getId()) {
                    return false;
                }
            }
        }
        return true;
    }


}
