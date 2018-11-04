package pl.put.poznan.analyzer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.logic.ConnectionRepository;
import pl.put.poznan.analyzer.logic.NodeRepository;

import java.util.Arrays;


@RestController
@RequestMapping("/{text}")
public class NetworkAnalyzerController {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerController.class);

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private NodeRepository nodeRepository;


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Node get(@PathVariable String text,
                    @RequestParam(value = "transforms", defaultValue = "upper,escape") String[] transforms) {
        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        return nodeRepository.findOne((long) 1);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                       @RequestBody String[] transforms) {
        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        return null;
    }


}


