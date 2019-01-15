package pl.put.poznan.analyzer.logic.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;

import java.util.*;

/**
 * This class is used to find the most profitable path from entry to exit using greedy algorithm.
 */

@Service
public class GreedyAlgorithm implements Algorithm {

    private List<Connection> patch;

    private static final Logger logger = LoggerFactory.getLogger(pl.put.poznan.analyzer.logic.algorithm.BFS.class);
    Map<Integer, Node> nodeMap;

    private boolean greedy(Node node) {
        ArrayList<Connection> sorted = new ArrayList(node.getOutgoing());
        sorted.sort((Connection o1, Connection o2) -> (int) (o1.getValue() - o2.getValue()));

        for (Connection con : sorted) {
            patch.add(con);
            Node nextNode = nodeMap.get(con.getTo());
            if (nextNode.getNodeType() == NodeType.EXIT) {
                return true;
            } else {
                if ( greedy(nextNode)){
                    return true;
                }else {
                    patch.remove(con);
                }
            }
        }
        return false;
    }

    /**
     * Find the most profitable path by using greedy algorithm.
     * <br> The method searches for the shortest path.
     *
     * @param nodesList network (list of nodes) in which you want to find the most profitable path
     * @return the most profitable path as Result (list of nodes and path's value)
     * <br> or NULL if path can't be found
     */
    @Override
    public Result run(List<Node> nodesList) {
        patch = new ArrayList<>();
        nodeMap = Data.getNodesMap(nodesList);
        Node start = Data.getEnterNode(nodeMap);
        boolean resultOk = greedy(start);

        if (resultOk) {
            float sum=0;
            ArrayList<Integer> resultPatch= new ArrayList<>();
            resultPatch.add(start.getId());
            for (Connection con: patch) {
                sum+=con.getValue();
                resultPatch.add(con.getTo());
            }
            logger.debug("The operation of the algorithm has been completed");
            return   new Result(sum, resultPatch);
        } else
            return null;
    }
}
