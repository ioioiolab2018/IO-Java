package pl.put.poznan.analyzer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.Result;
import pl.put.poznan.analyzer.logic.NetworkAnalyzer;

import java.util.List;


@RestController
@RequestMapping("")
public class NetworkAnalyzerController {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerController.class);
    private NetworkAnalyzer networkAnalyzer;

    @Autowired
    public NetworkAnalyzerController(NetworkAnalyzer networkAnalyzer) {
        this.networkAnalyzer = networkAnalyzer;
    }

    @RequestMapping(path = "/bfs/nodes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Result findTheBestPathByBFS(@RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(nodes));
        return networkAnalyzer.findTheBestPath(nodes, "BFS");
    }

    @RequestMapping(path = "/dfs/nodes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Result findTheBestPathByDFS(@RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(nodes));
        return networkAnalyzer.findTheBestPath(nodes, "DFS");
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public int addNewNetwork(@RequestBody String nodesJson) {
        logger.debug(nodesJson);
        return networkAnalyzer.addNetwork(nodesJson);
    }

}


