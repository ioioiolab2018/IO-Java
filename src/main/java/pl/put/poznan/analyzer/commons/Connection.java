package pl.put.poznan.analyzer.commons;

import javax.persistence.*;

@Entity()
@Table(name = "CONNECTIONS")
public class Connection {

    @Column
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_NODE_ID")
    private Node from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_NODE_ID")
    private Node to;

    @Column
    private Float value;


    public Connection(Node from, Node to, Float value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "from: " + from +
                ", to: " + to +
                ", value: " + value +
                '}';
    }
}
