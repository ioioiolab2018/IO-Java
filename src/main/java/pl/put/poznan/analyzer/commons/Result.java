package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;

/**
 * This class is used to store and manage shortest paths from traversing graphs.
 */
public class Result {
    /**
     * Total value of the shortest path
     */
    private float value;
    /**
     * List of nodes from the shortest path
     */
    private ArrayList<Integer> nodes;

    /**
     * Empty class contructor
     */
    public Result() {
    }

    /**
     * Class constructor
     *
     * @param value total value of the shortest path
     * @param nodes list of nodes from the shortest path
     */
    public Result(float value, ArrayList<Integer> nodes) {
        this.value = value;
        this.nodes = nodes;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }
}
