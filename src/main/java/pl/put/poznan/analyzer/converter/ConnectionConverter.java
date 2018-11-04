package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.put.poznan.analyzer.commons.Connection;

import java.io.IOException;

@Component
public class ConnectionConverter implements Converter<String, Connection> {
    @Override
    public Connection convert(String s) {
        try {
            return new ObjectMapper().readValue(s, Connection.class);
        } catch (IOException e) {
            return null;
        }
    }
}
