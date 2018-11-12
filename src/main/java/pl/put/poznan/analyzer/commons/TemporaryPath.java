package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;

public class TemporaryPath {
    private List<Connection> resultList;
    private float value;

    public TemporaryPath() {
        resultList = new ArrayList<>();
    }

    public TemporaryPath(TemporaryPath temporaryPath) {
        value = temporaryPath.value;
        resultList = new ArrayList<>();
        for (Connection connection : temporaryPath.getResultList()) {
            resultList.add(new Connection(connection));
        }
    }

    public TemporaryPath(List<Connection> resultList) {
        this.resultList = resultList;
        float sum = 0;
        for (Connection connection: resultList) {
            sum += connection.getValue();
        }
        this.value = sum;
    }

    public List<Connection> getResultList() {
        return resultList;
    }

    public void setResultList(List<Connection> resultList) {
        this.resultList = resultList;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void add(Connection connection) {
        value += connection.getValue();
        resultList.add(connection);
    }

    public void remove(Connection connection) {
        if (resultList.remove(connection)) {
            value -= connection.getValue();
        }
    }

    public Result getResult() {
        ArrayList<Integer> nodes = new ArrayList<>();
        if (resultList.get(0) != null) {
            nodes.add(resultList.get(0).getFrom());
        } else {
            throw new IllegalStateException();
        }
        for (Connection connection : resultList) {
            nodes.add(connection.getTo());
        }
        return new Result(value, nodes);
    }
}
