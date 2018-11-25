package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to manage a single node
 */
public class Node {

    /**
     * identifier number (id) of the node
     */
    private int id;
    /**
     * optional description of the node
     */
    private String name;
    /**
     * type of the node, it can be ENTRY, EXIT, REGULAR or extra type ADDITIONAL
     */
    private NodeType nodeType;
    /**
     * list of outgoing connections from the node
     */
    private List<Connection> outgoing;
    /**
     * list of incoming connections to the node
     */
    private List<Connection> incoming;


    /**
     * Class constructor for nodes with connections
     * @param id node id
     * @param name optional description of the node
     * @param nodeType node type can be ENTRY, EXIT or REGULAR
     * @param outgoing list of outgoing connections from the node
     * @param incoming list of incoming connections to the node
     */
    public Node(Integer id, String name, NodeType nodeType, List<Connection> outgoing, List<Connection> incoming) {
        this.id = id;
        this.name = name;
        this.nodeType = nodeType;
        this.outgoing = outgoing;
        this.incoming = incoming;
    }

    /**
     * Class constructor for nodes without connections
     * @param id node id
     * @param name optional description of the node
     * @param nodeType node type can be ENTRY, EXIT or REGULAR
     */
    public Node(Integer id, String name, NodeType nodeType) {
        this.id = id;
        this.name = name;
        this.nodeType = nodeType;
        this.outgoing = new ArrayList<>();
        this.incoming = new ArrayList<>();
    }

    // setters getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public List<Connection> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(List<Connection> outgoing) {
        this.outgoing = outgoing;
    }

    public List<Connection> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<Connection> incoming) {
        this.incoming = incoming;
    }

    /**
     * Empty class constructor
     */
    public Node() {
    }

    public Node(int id, String name, NodeType nodeType, List<Connection> outgoing, List<Connection> incoming) {
        this.id = id;
        this.name = name;
        this.nodeType = nodeType;
        this.outgoing = outgoing;
        this.incoming = incoming;
    }

    /**
     * Override equals method
     * @param o object to compare with
     * @return TRUE, when objects are identical
     *      <br>FALSE, when objects are different
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    /**
      * Override hashcode method
      * @return hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, nodeType, outgoing, incoming);
    }

    /**
     * Override toString method
     * @return string with all information about the object
     */
    @Override
    public String toString() {
        return "Node{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", nodeType: " + nodeType +
                ", outgoing: " + outgoing +
                ", incoming: " + incoming +
                '}';
    }


}
