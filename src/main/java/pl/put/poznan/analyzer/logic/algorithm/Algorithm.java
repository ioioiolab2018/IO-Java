package pl.put.poznan.analyzer.logic.algorithm;

import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;

import java.util.List;

public interface Algorithm {
    Result run(List<Node> nodes);
}
