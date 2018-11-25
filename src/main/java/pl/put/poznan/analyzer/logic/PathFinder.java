package pl.put.poznan.analyzer.logic;

import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;
import pl.put.poznan.analyzer.logic.algorithm.Algorithm;

import java.util.List;

@Service
public class PathFinder {
    private Algorithm algorithm;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Result findPath(List<Node> nodeMap) {
        return algorithm.run(nodeMap);
    }
}
