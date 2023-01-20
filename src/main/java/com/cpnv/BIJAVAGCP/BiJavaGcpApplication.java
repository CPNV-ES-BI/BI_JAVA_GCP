package com.cpnv.BIJAVAGCP;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BiJavaGcpApplication {

    public static void main(String[] args)  {
        //SpringApplication.run(BiJavaGcpApplication.class, args);
        DataObjectController dataObjectController = new DataObjectController();
        dataObjectController.list();

        

    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
