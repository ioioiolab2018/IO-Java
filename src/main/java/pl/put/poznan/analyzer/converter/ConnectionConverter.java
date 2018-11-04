package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import pl.put.poznan.analyzer.commons.Connection;

import java.io.IOException;

public class ConnectionConverter implements Converter<String, Connection> {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionConverter.class);

    @Override
    public Connection convert(String s) {
        try {
            return new ObjectMapper().readValue(s, Connection.class);
        } catch (IOException e) {
            logger.debug("PROBLEM WITH CONVERSION");
            throw new IllegalArgumentException("Incorrect JSON value!");
        }
    }
}
