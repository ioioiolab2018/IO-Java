package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Node> nodes;
    private List<Connection> connections;

    public void addConnection(Node nodeA, Node nodeB, Float value) {
        this.connections.add(new Connection(nodeA, nodeB, value));
        if (this.nodes.indexOf(nodeA) == -1) {
            this.nodes.add(nodeA);
        }
        if (this.nodes.indexOf(nodeB) == -1) {
            this.nodes.add(nodeB);
        }
    }

    public void addNode(String name, NodeType nodeType) {
        int max = 0;
        for (Node node : nodes) {
            if (node.getId() >= max) {
                max = node.getId();
            }
        }
        int id = max + 1;
        if (nodeType == NodeType.REGULAR) {
            nodes.add(new Node(id, name, nodeType));
        } else {
            boolean ok = true;
            for (Node node : nodes) {
                if (node.getNodeType() == nodeType) {
                    ok = false;
                }
            }
            if (ok) {
                nodes.add(new Node(id, name, nodeType));
            }
        }
    }

    public Node getNode(int nodeId) {
        for (Node node : nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }

    public Node getEntry() {
        for (Node node : nodes
        ) {
            if (node.getNodeType() == NodeType.ENTRY) {
                return node;
            }
        }
        return null;
    }

    public Data() {
        this.nodes = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
