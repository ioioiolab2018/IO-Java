package pl.put.poznan.analyzer.commons;

import java.util.Objects;

public class Connection {


    private int from;
    private int to;
    private float value;

    public Connection() {
    }

    public Connection(Connection connection) {
        from = connection.getFrom();
        to = connection.getTo();
        value = connection.getValue();
    }

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
