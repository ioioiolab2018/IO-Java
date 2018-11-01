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
    private Type type;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
                ", type: " + type +
                ", outgoing: " + outgoing +
                ", incoming: " + incoming +
                '}';
    }
}
