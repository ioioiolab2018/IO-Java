package pl.put.poznan.analyzer.commons;

import java.util.Objects;

/**
 * This class is used to manage a single connection between two nodes
 */
public class Connection {


    private int from;
    private int to;
    private Float value;

    /**
     * Class constructor
     * @param from node from which the connection starts
     * @param to node in which the connection ends
     * @param value value of the connection
     */
    public Connection(int from, int to, Float value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    /**
     * Empty class constructor
     */
    public Connection() {
    }

    @Override
    public String toString() {
        return "Connection{" +
                "from: " + from +
                ", to: " + to +
                ", value: " + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, value);
    }
}
