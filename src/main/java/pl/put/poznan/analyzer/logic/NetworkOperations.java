package pl.put.poznan.analyzer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Network;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.converter.NodeListConverter;
import pl.put.poznan.analyzer.repositories.NetworkRepository;

import java.util.ArrayList;
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
     * @param id Network identifier which is stored in the database
     * @param nodes List of Nodes and Connections to be added
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
                if (node != null) {
                    node.setIncoming(Stream
                            .concat(node.getIncoming().stream(),
                                    nodeEntry.getValue().getIncoming().stream()
                            ).distinct()
                            .collect(Collectors.toList()));
                    node.setOutgoing(Stream
                            .concat(node.getOutgoing().stream(),
                                    nodeEntry.getValue().getOutgoing().stream()
                            ).distinct()
                            .collect(Collectors.toList()));
                } else {
                    oldNodeMap.put(nodeEntry.getKey(), nodeEntry.getValue());
                    node = nodeEntry.getValue();
                    node.getIncoming()
                            .forEach(connection -> Data.getNodeById(oldNodeMap, connection.getFrom())
                                    .getOutgoing()
                                    .add(connection));
                    node.getOutgoing()
                            .forEach(connection -> Data.getNodeById(oldNodeMap, connection.getTo())
                                    .getIncoming()
                                    .add(connection));
                }
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
