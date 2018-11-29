package pl.put.poznan.analyzer.logic.algorithm;

import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;

import java.util.List;

/**
 * An interface that allows you to find a path in the network.
 */
public interface Algorithm {
    /**
     * Find the most profitable path in the network.
     *
     * @param nodes Network (list of nodes).
     * @return Path as Result (list of nodes and path's value).
     */
    Result run(List<Node> nodes);
}
