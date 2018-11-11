package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.put.poznan.analyzer.commons.Node;

import java.io.IOException;
import java.util.List;

@Component
public class NodeListConverter implements Converter<String, List<Node>> {
    private static final Logger logger = LoggerFactory.getLogger(NodeListConverter.class);

    @Override
    public List<Node> convert(String s) {
        try {
            return new ObjectMapper().readValue(s, new TypeReference<List<Node>>(){});
        } catch (IOException e) {
            logger.debug("PROBLEM WITH CONVERSION");
            throw new IllegalArgumentException("Incorrect JSON value!");
        }
    }
}
