package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Additional class to help with DFS
 */
public class TemporaryPath {
    /**
     * Current path as list of connections
     */
    private List<Connection> resultList;
    /**
     * Value of the path
     */
    private float value;

    /**
     * Empty class constructor
     */
    public TemporaryPath() {
        resultList = new ArrayList<>();
    }

    /**
     * Class constructor used to make a deep copy of the object
     *
     * @param temporaryPath temporary path on which you want to make deep copy
     */
    public TemporaryPath(TemporaryPath temporaryPath) {
        value = temporaryPath.value;
        resultList = new ArrayList<>();
        for (Connection connection : temporaryPath.getResultList()) {
            resultList.add(new Connection(connection));
        }
    }

    /**
     * Class constructor which calculates value of the path
     *
     * @param resultList path (list of connections) which you want to save as temporary path
     */
    public TemporaryPath(List<Connection> resultList) {
        this.resultList = resultList;
        float sum = 0;
        for (Connection connection : resultList) {
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

    /**
     * Add connection to the current path (and calculate new value of the path)
     *
     * @param connection connection which you want to add to the path
     */
    public void add(Connection connection) {
        value += connection.getValue();
        resultList.add(connection);
    }

    /**
     * Remove connection from the current path (and calculate new value of the path)
     *
     * @param connection connection which you want to remove from the path
     */
    public void remove(Connection connection) {
        if (resultList.remove(connection)) {
            value -= connection.getValue();
        }
    }

    /**
     * Transform path into Result (list of nodes and path's value)
     *
     * @return path as Result (list of nodes and path's value)
     */
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
