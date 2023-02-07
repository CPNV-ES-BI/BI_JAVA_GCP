package com.cpnv.bijavagcp;

import com.cpnv.bijavagcp.config.GcpConfiguration;

import java.io.IOException;
import com.google.cloud.storage.Storage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiJavaGcpApplication {
    public static void main(String[] args) throws IOException {
       // SpringApplication.run(BiJavaGcpApplication.class, args);
        Storage storage = GcpConfiguration.getStorage();
    }
}
