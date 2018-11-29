package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;
import pl.put.poznan.analyzer.logic.algorithm.Algorithm;

import java.util.List;

/**
 * This class allows you to find the path between the entry node and the exit node.
 */
@Service
public class PathFinder {
    private static final Logger logger = LoggerFactory.getLogger(PathFinder.class);

    /**
     * The algorithm used to find the path.
     */
    private Algorithm algorithm;

    /**
     * Method that sets the algorithm used to find a path in the network.
     *
     * @param algorithm Object that implements the path finding algorithm.
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Find the most profitable path in the network.
     *
     * @param nodeList Network (list of nodes) in which you want to find the best path.
     * @return The best path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found.
     */
    public Result findPath(List<Node> nodeList) {
        checkNetworkCorrectness(nodeList);
        return algorithm.run(nodeList);
    }

    /**
     * Checks validity of the network (when given network is a list of nodes).
     *
     * @param nodeList Network (list of nodes).
     */
    private void checkNetworkCorrectness(List<Node> nodeList) {
        logger.debug("Checking the correctness of the network");
        if (!Data.checkNetwork(nodeList)) {
            logger.error("Incorrect network");
            throw new IllegalArgumentException("Incorrect network");
        }
        logger.debug("Prepared to run the algorithm");
    }
}
