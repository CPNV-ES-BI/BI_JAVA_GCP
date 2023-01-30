package com.cpnv.BIJAVAGCP;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import com.cpnv.BIJAVAGCP.Object.ObjectAlreadyExistsException;
import com.cpnv.BIJAVAGCP.Object.ObjectNotExistsException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BiJavaGcpApplication {

    public static void main(String[] args) throws ObjectAlreadyExistsException, ObjectNotExistsException {
        //SpringApplication.run(BiJavaGcpApplication.class, args);
        DataObjectController object = new DataObjectController();
        object.setBucketName("bi.java.cld.education");
        object.list();
    }
    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
