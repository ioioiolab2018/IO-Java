package pl.put.poznan.analyzer.commons;

public class Connection {


    private Node from;
    private Node to;
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
