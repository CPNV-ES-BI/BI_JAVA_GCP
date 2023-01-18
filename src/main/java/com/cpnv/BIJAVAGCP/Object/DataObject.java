package com.cpnv.BIJAVAGCP.Object;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


public interface DataObject {

    void list();
    void create(String fileName) throws IOException;
    boolean isExist(String fileName);
    void delete(String fileName);
}
