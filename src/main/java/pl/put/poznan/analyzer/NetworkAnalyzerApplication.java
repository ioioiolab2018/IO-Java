package pl.put.poznan.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
public class NetworkAnalyzerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NetworkAnalyzerApplication.class);
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(NetworkAnalyzerApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        String protocol = "http";
        logger.info("Application start\n" +
                        "====================================================================================================" +
                        "==================================================================================================\n" +
                        "Application '{}' is running! Information and access URLs:\n\t" +
                        "Name: \t\t{}\n\t" +
                        "Version: \t1.0.1\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Date: \t\t" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "\n" +
                        "====================================================================================================" +
                        "================================================================================================\n\n",
                environment.getProperty("spring.application.name"),
                environment.getProperty("spring.application.name"),
                protocol,
                environment.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
    }

}
