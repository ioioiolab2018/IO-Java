package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Connection> resultList;
    private float value;

    public Result() {
        resultList = new ArrayList<>();
    }

    public Result(Result result) {
        value = result.value;
        resultList = new ArrayList<>();
        for (Connection connection : result.getResultList()) {
            resultList.add(new Connection(connection));
        }
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
}
