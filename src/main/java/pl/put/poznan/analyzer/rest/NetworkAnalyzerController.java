package pl.put.poznan.analyzer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.analyzer.commons.Connection;
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

    @RequestMapping(path = "/saveNetwork", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public int saveNetwork(@RequestBody String nodesJson) {
        logger.debug(nodesJson);
        return networkAnalyzer.saveNetworkOnDatabase(nodesJson);
    }

    @RequestMapping(path = "/getNetwork/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String getNetwork(@PathVariable int id) {
        logger.debug(String.valueOf(id));
        return networkAnalyzer.getNetwork(id);
    }

    @RequestMapping(path = "/deleteNetwork/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteNetwork(@PathVariable int id) {
        logger.debug(String.valueOf(id));
        networkAnalyzer.deleteNetworkFromDatabase(id);
    }

    @RequestMapping(path = "/addNodes/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Node> addNodesToNetwork(@PathVariable int id, @RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(id) + ": " + String.valueOf(nodes));
        return networkAnalyzer.addNodesToNetwork(id, nodes);
    }

    @RequestMapping(path = "/addConnections/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Node> addConnectionsToNetwork(@PathVariable int id, @RequestBody List<Connection> connections) {
        logger.debug(String.valueOf(id) + ": " + String.valueOf(connections));
        return networkAnalyzer.addConnectionsToNetwork(id, connections);
    }

    @RequestMapping(path = "/deleteNodes/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Node> deleteNodesFromNetwork(@PathVariable int id, @RequestBody List<Node> nodes) {
        logger.debug(String.valueOf(id) + ": " + String.valueOf(nodes));
        return networkAnalyzer.deleteNodesFromNetwork(id, nodes);
    }

    @RequestMapping(path = "/deleteConnections/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Node> deleteConnectionsFromNetwork(@PathVariable int id, @RequestBody List<Connection> connections) {
        logger.debug(String.valueOf(id) + ": " + String.valueOf(connections));
        return networkAnalyzer.deleteConnectionsFromNetwork(id, connections);
    }
}


