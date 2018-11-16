package pl.put.poznan.analyzer.commons;

import java.util.Objects;

/**
 * This class is used to manage a single connection between two nodes
 */
public class Connection {


    /**
     * Node id from which the connection starts
     */
    private int from;

    /**
     * Node id in which the connection ends
     */
    private int to;

    /**
     * Value of the connection
     */
    private float value;

    /**
     * Empty class constructor
     */
    public Connection() {
    }

    /**
     * Class constructor used to make a deep copy of the object
     *
     * @param connection connection on which you want to make deep copy
     */
    public Connection(Connection connection) {
        from = connection.getFrom();
        to = connection.getTo();
        value = connection.getValue();
    }

    /**
     * Class constructor
     *
     * @param from  node id from which the connection starts
     * @param to    node id in which the connection ends
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
     * Override toString method
     *
     * @return string with all information about the object
     */
    @Override
    public String toString() {
        return "Connection{" +
                "from: " + from +
                ", to: " + to +
                ", value: " + value +
                '}';
    }

    /**
     * Override equals method
     *
     * @param o object to compare with
     * @return TRUE, when objects are identical
     * <br>FALSE, when objects are different
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(value, that.value);
    }

    /**
     * Override hashcode method
     *
     * @return hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, value);
    }
}
