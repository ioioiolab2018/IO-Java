package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import pl.put.poznan.analyzer.commons.Node;

import java.io.IOException;

public class NodeConverter implements Converter<String, Node> {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionListConverter.class);

    @Override
    public Node convert(String s) {
        try {
            return new ObjectMapper().readValue(s, Node.class);
        } catch (IOException e) {
            logger.debug("PROBLEM WITH CONVERSION");
            throw new IllegalArgumentException("Incorrect JSON value!");
        }
    }
}
