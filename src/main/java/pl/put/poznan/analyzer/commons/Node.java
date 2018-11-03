package pl.put.poznan.analyzer.commons;

import javax.persistence.*;
import java.util.List;

@Entity(name = "NODES")
public class Node {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private NodeType nodeType;

    @Column
    @OneToMany(mappedBy = "from")
    private List<Connection> outgoing;

    @Column
    @OneToMany(mappedBy = "to")
    private List<Connection> incoming;

    // setters getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
