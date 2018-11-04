package pl.put.poznan.analyzer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Node;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("")
public class NetworkAnalyzerController {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerController.class);

    @RequestMapping(path = "/test/{connections}", method = RequestMethod.GET, produces = "application/json")
    public Node get(@PathVariable List<Connection> connections,
                    @RequestParam(value = "transforms", defaultValue = "upper,escape") String[] transforms) {
        // log the parameters
        logger.debug(String.valueOf(connections));
        logger.debug(Arrays.toString(transforms));
        return null;
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


