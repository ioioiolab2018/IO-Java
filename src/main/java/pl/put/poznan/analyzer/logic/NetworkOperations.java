package pl.put.poznan.analyzer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.*;
import pl.put.poznan.analyzer.converter.NodeListConverter;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is a service that provides methods to perform operations on a saved network in a database.
 */
@Service
public class NetworkOperations {
    private static final Logger logger = LoggerFactory.getLogger(NetworkOperations.class);

    /**
     * Instance of CrudRepository interface, which is used to find, save and delete networks
     */
    private final NetworkRepository networkRepository;

    /**
     * Instance of NodeListConverter class, which is used to convert JSON to an object
     */
    private final NodeListConverter nodeListConverter;

    /**
     * Instance of ObjectMapper class, which is used to convert object to an JSON
     */
    private final ObjectMapper objectMapper;

    /**
     * class constructor for spring
     *
     * @param networkRepository instance of NetworkRepository to be used in program
     * @param nodeListConverter instance of NodeListConverter to be used in program
     */
    @Autowired
    public NetworkOperations(NetworkRepository networkRepository, NodeListConverter nodeListConverter) {
        this.networkRepository = networkRepository;
        this.nodeListConverter = nodeListConverter;
        objectMapper = new ObjectMapper();
    }

    /**
     * Add Nodes and Connection between Nodes to network which is saved in the database
     *
     * @param id    Network identifier which is stored in the database
     * @param nodes List of Nodes and Connections to be added to the Network
     * @return Modified List of Nodes
     */
    public List<Node> addNodesToNetwork(int id, List<Node> nodes) {
        Network network = networkRepository.findOne(id);

        if (network != null) {
            Map<Integer, Node> nodeMap = Data.getNodesMap(nodes);

            List<Node> temp = mapStringToNodeList(network.getJsonValue());
            Map<Integer, Node> oldNodeMap = Data.getNodesMap(temp);

            for (Map.Entry<Integer, Node> nodeEntry : nodeMap.entrySet()) {
                Node node = oldNodeMap.get(nodeEntry.getKey());

                if (node == null) {
                    oldNodeMap.put(nodeEntry.getKey(), nodeEntry.getValue());
                    node = nodeEntry.getValue();

                    if (node.getNodeType().equals(NodeType.ENTRY)) {
                        Data.getEnterNode(oldNodeMap).setNodeType(NodeType.REGULAR);
                    } else if (node.getNodeType().equals(NodeType.EXIT)) {
                        Data.getExitNode(oldNodeMap).setNodeType(NodeType.REGULAR);
                    }
                }

                addConnections(
                        oldNodeMap,
                        Stream.concat(node.getIncoming().stream(), node.getOutgoing().stream())
                                .distinct()
                                .collect(Collectors.toList()));
            }

            if (!Data.checkNetwork(oldNodeMap)) {
                logger.error("Incorrect network");
                throw new IllegalArgumentException("Incorrect network");
            }

            List<Node> newNetwork = new ArrayList<>(oldNodeMap.values());
            network.setJsonValue(mapNodeListToJSON(newNetwork));
            networkRepository.save(network);
            return newNetwork;
        }

        throw new IllegalStateException("There is no network with the given id");
    }

    /**
     * Add Connections between Nodes to network which is saved in the database
     *
     * @param id          Network identifier which is stored in the database
     * @param connections List of Connections to be added to the Network
     * @return Modified Network
     */
    public List<Node> addConnectionsToNetwork(Integer id, List<Connection> connections) {
        Network network = networkRepository.findOne(id);

        if (network != null) {
            List<Node> temp = mapStringToNodeList(network.getJsonValue());
            Map<Integer, Node> nodeMap = Data.getNodesMap(temp);

            addConnections(nodeMap, connections);

            if (!Data.checkNetwork(nodeMap)) {
                logger.error("Incorrect network");
                throw new IllegalArgumentException("Incorrect network");
            }

            List<Node> newNetwork = new ArrayList<>(nodeMap.values());
            network.setJsonValue(mapNodeListToJSON(newNetwork));
            networkRepository.save(network);
            return newNetwork;
        }

        throw new IllegalStateException("There is no network with the given id");
    }

    /**
     * @param nodes       Map of Nodes on the Network
     * @param connections List of Connection to be added
     */
    private void addConnections(Map<Integer, Node> nodes, List<Connection> connections) {
        connections.forEach(connection -> {
            Node node = Data.getNodeById(nodes, connection.getFrom());
            node.setOutgoing(
                    Stream.concat(
                            node.getOutgoing().stream(),
                            Stream.of(connection))
                            .distinct()
                            .collect(Collectors.toList()));

            node = Data.getNodeById(nodes, connection.getTo());
            node.setIncoming(
                    Stream.concat(
                            node.getIncoming().stream(),
                            Stream.of(connection))
                            .distinct()
                            .collect(Collectors.toList()));
        });
    }

    /**
     * @param json JSON value represents List of Nodes
     * @return Converted value
     */
    private List<Node> mapStringToNodeList(String json) {
        return nodeListConverter.convert(json);
    }

    /**
     * @param nodeList List of Nodes
     * @return Converted value
     */
    private String mapNodeListToJSON(List<Node> nodeList) {
        try {
            return objectMapper.writeValueAsString(nodeList);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("An unexpected error occurred while mapping the object to string");
        }
    }
}
