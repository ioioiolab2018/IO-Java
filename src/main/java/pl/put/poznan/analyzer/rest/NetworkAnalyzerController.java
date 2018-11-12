package pl.put.poznan.analyzer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;
import pl.put.poznan.analyzer.logic.BFS;
import pl.put.poznan.analyzer.logic.NetworkAnalyzer;

import java.util.List;


@RestController
@RequestMapping("")
public class NetworkAnalyzerController {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerController.class);

    @RequestMapping(path = "/bfs/nodes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Connection> findTheBestPathByBFS(@RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(nodes));
        if (!Data.checkNetwork(nodes)) {
            logger.error("Incorrect network");
            throw new IllegalArgumentException("Incorrect network");
        }
        return BFS.run(nodes);
    }

    @RequestMapping(path = "/dfs/nodes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Result findTheBestPathByDFS(@RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(nodes));
        if (!Data.checkNetwork(nodes)) {
            logger.error("Incorrect network");
            throw new IllegalArgumentException("Incorrect network");
        }
        return NetworkAnalyzer.findTheBestPath(nodes, "DFS");
    }

}


