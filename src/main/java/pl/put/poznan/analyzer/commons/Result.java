package pl.put.poznan.analyzer.commons;

import java.util.ArrayList;

public class Result {
    private float value;
    private ArrayList<Integer> nodes;

    public Result() {
    }

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
