package pl.put.poznan.analyzer.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.put.poznan.analyzer.commons.Node;

import java.io.IOException;

@Component
public class NodeConverter implements Converter<String, Node> {
    @Override
    public Node convert(String s) {
        try {
            return new ObjectMapper().readValue(s, Node.class);
        } catch (IOException e) {
            return null;
        }
    }
}
