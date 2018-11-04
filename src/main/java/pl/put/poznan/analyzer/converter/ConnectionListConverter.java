package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.put.poznan.analyzer.commons.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionListConverter implements Converter<String, List<Connection>> {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionListConverter.class);

    @Override
    public List<Connection> convert(String s) {
        try {
            return new ObjectMapper().readValue(s, new TypeReference<List<Connection>>(){});
        } catch (IOException e) {
            logger.debug("PROBLEM WITH CONVERSION");
            throw new IllegalArgumentException("Incorrect JSON value!");
        }
    }
}
