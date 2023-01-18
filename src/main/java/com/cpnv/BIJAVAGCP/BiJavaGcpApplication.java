package com.cpnv.BIJAVAGCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BiJavaGcpApplication {

    public static void main(String[] args) {
        //SpringApplication.run(BiJavaGcpApplication.class, args);
        BucketController bucketController = new BucketController();
        bucketController.list();

    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}