package pl.put.poznan.analyzer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;
import pl.put.poznan.analyzer.logic.algorithm.Algorithm;

import java.util.List;

@Service
public class PathFinder {
    private static final Logger logger = LoggerFactory.getLogger(PathFinder.class);

    private Algorithm algorithm;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Result findPath(List<Node> nodeList) {
        checkNetworkCorrectness(nodeList);
        return algorithm.run(nodeList);
    }

    private void checkNetworkCorrectness(List<Node> nodeList) {
        logger.debug("Checking the correctness of the network");
        if (!Data.checkNetwork(nodeList)) {
            logger.error("Incorrect network");
            throw new IllegalArgumentException("Incorrect network");
        }
        logger.debug("Prepared to run the algorithm");
    }
}
