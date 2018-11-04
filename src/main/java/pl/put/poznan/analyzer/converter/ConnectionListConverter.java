package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.put.poznan.analyzer.commons.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionListConverter implements Converter<String, List<Connection>> {
    @Override
    public List<Connection> convert(String s) {
        try {
            return new ObjectMapper().readValue(s, new TypeReference<List<Connection>>(){});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
