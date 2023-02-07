package com.cpnv.bijavagcp.config;

import com.cpnv.bijavagcp.BiJavaGcpApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GcpConfiguration {

   private String bucketName;
   private static String projectId;
   private static String accessKeyId;
    private static String accessKey;
    private static String clientId;
    private static String clientEmail;


    public String getBucketName() {
        return bucketName;
    }

    public static String getProjectId() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        return properties.getProperty("GCP_PROJECT_ID");
    }

    public static String getAccessKeyId() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        return properties.getProperty("GCP_ACCESS_KEY_ID");
    }

    public static String getAccessKey() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        return properties.getProperty("GCP_SECRET_ACCESS_KEY");
    }

    public static String getClientId() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        return properties.getProperty("clientId");
    }
    public static String getClientEmail() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        return properties.getProperty("clientEmail");
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public static Storage getStorage() throws IOException {
        String projectId = getProjectId();
        File credentialsPath = new File("src/main/resources/access-key.json");
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
        }
System.out.println(projectId);
        System.out.println(getClientEmail());

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }

}
