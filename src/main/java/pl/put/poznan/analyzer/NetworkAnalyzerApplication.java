package pl.put.poznan.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.analyzer.logic.ConnectionRepository;
import pl.put.poznan.analyzer.logic.NodeRepository;


@SpringBootApplication
public class NetworkAnalyzerApplication implements CommandLineRunner {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private NodeRepository nodeRepository;

    public static void main(String[] args) {
        SpringApplication.run(NetworkAnalyzerApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        //Początkowe

    }

}
