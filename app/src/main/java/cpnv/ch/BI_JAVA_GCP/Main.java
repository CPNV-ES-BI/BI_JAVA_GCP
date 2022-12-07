package cpnv.ch.BI_JAVA_GCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Properties;

@SpringBootApplication
@RestController
public class Main {
    private static ListBuckets buckets;
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        Properties prop = new Properties();
        String propFileName = "config.properties";
        String projectId = prop.getProperty("projectId");

        LinkedList listBuckes = buckets.listBuckets(projectId);

    }
}
