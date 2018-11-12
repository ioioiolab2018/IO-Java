package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is used to manage a network of nodes
 */
public class Data {

    /**
     * Changes network format from list of nodes to hashMap
     * @param nodes the network as the list of nodes
     * @return the network as hashMap
     */
    public static Map<Integer, Node> getNodesMap(List<Node> nodes){
        Map<Integer, Node> nodesMap = new HashMap<>();
        for (Node node: nodes) {
            if(nodesMap.putIfAbsent(node.getId(), node)!=null){
                throw new IllegalStateException("A repeating vertex was found!");
            }
        }
        return nodesMap;
    }

    /**
     * Get a specific node from the network (hashmap) by giving its id
     * @param nodes network as a hashMap in which wanted node is
     * @param id id of wanted node
     * @return wanted node
     */
    public static Node getNodeById(Map<Integer, Node> nodes, int id) {
        return nodes.get(id);
    }

    /**
     * Get the entry node from the network (hashmap)
     * @param nodes network as a hashMap in which wanted node is
     * @return the entry node
     */
    public static  Node getEnterNode(Map<Integer,Node> nodes){

        for(Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue();
            if(node.getNodeType()== NodeType.ENTRY){
                return node;
            }
        }
        throw new IllegalStateException("The entry node was not found!");
    }

    /**
     * Checks validity of the network (when given network is a hashMap),
     * which consists of:
     *  <br>- checking if there is only one entry node
     *  <br>- checking if there is only one exit node
     *  <br>- checking validity of nodes ids
     *
     * @param nodes the network (as a hashMap) to be checked for validity
     * @return TRUE, when the network is valid
     *         <br>FALSE, when the network is invalid
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


    /**
     * Get list of all connections from the network (list of nodes)
     * @param nodes network from which you want to get all connections
     * @return list of all connections from the network
     */
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

    /**
     * Get maximum id from the network (list of nodes)
     * @param nodes network as a list of nodes from which you want to get maximum id
     * @return maximum id
     */
    public static int getMaxId(List<Node> nodes){
        int max =0;
        for (Node node: nodes) {
            if (node.getId()>=max){
                max=node.getId();
            }
        }
        return max;
    }

    /**
     * Get a specific node from the network (list of nodes) by giving its id
     * @param nodes network as a list of nodes in which wanted node is
     * @param id id of wanted node
     * @return wanted node
     */
    public static Node getNodeById(List<Node> nodes, int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        throw (new IllegalStateException());
    }

    /**
     * Get the entry node from the network (list of nodes)
     * @param nodes nodes network as a list of nodes in which wanted node is
     * @return entry node
     */
    public static  Node getEnterNode(List<Node> nodes){
        for (Node node:nodes) {
            if (node.getNodeType()== NodeType.ENTRY){
                return node;
            }
        }
        return  null;
    }

    /**
     * Checks validity of the network (when given network is a list of nodes)
     * which consists of:
     *  <br>- checking if there is only one entry node
     *  <br>- checking if there is only one exit node
     *  <br>- checking validity of nodes ids
     * @param nodes the network (as a list of nodes) to be checked for validity
     * @return TRUE, when the network is valid
     *         <br>FALSE, when the network is invalid
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

    /**
     * Change weighted network to pseudo-not-weighted one. The connections of values greater than 1 are broken
     * into few smaller connections (where value is interpreted as 1).
     * In practice, connections where a starting node is from original graph still have original values
     * (It's specifically made like this to help with finding a value of shortest path in BFS).
      * @param nodesList network to be transformed into network with connections' values equal to 1
     * @return network with connections' values equal to 1
     */
    public static Map<Integer, Node> changeToUnweighted (List<Node> nodesList) {
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
                        additional.getOutgoing().add(new Connection(additional.getId(), id, (float)1));
                    } else {
                        nodesId -= 1;
                        additional.getOutgoing().add(new Connection(additional.getId(), nodesId, (float)1));
                        while (true) {
                            Node last = new Node(nodesId, "Additional", NodeType.ADDITIONAL);
                            newList.add(last);
                            value -= 1;
                            if (value <= 1) {
                                last.getOutgoing().add(new Connection(last.getId(), id, (float) 1));
                                break;
                            } else {
                                last.getOutgoing().add(new Connection(nodesId, nodesId - 1, (float) 1));
                                nodesId -= 1;
                            }
                        }
                    }
                } else {
                    newNode.getOutgoing().add(new Connection(newNode.getId(), id, realValue));
                }
            }
        }

        return Data.getNodesMap(newList);
    }

}
