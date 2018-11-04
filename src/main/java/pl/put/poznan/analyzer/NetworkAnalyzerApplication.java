package pl.put.poznan.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NetworkAnalyzerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NetworkAnalyzerApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        logger.info("Application started");
        //Początkowe

    }

}
