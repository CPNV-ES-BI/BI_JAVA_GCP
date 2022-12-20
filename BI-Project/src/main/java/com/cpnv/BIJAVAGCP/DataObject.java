package com.cpnv.BIJAVAGCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


public interface DataObject {

    public void createObject(String projectId, String bucketName)
    public void deleteObject(String projectId, String bucketName, String objectName)
    public void listObjects(String projectId, String bucketName)
    public String downloadObject(String projectId, String bucketName, String objectName, String destFilePath)
    public void publishObject(String projectId, String bucketName, String objectName, String filePath)
}
