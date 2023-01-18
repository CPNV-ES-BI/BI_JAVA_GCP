package com.cpnv.BIJAVAGCP;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class BiJavaGcpApplication {

    public static void main(String[] args) throws IOException {
        //SpringApplication.run(BiJavaGcpApplication.class, args);
        DataObjectController dataObjectController = new DataObjectController();
        dataObjectController.list();
        dataObjectController.isExist("test.txt");

    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
