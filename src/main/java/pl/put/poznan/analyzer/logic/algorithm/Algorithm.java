package pl.put.poznan.analyzer.logic.algorithm;

import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;

import java.util.Map;

public interface Algorithm {
    Result run(Map<Integer, Node> nodes);
}
