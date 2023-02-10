package com.cpnv.bijavagcp.config;

import com.cpnv.bijavagcp.BiJavaGcpApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.Properties;

public class GcpConfiguration {

    private final String projectId;
    private final String accessKeyId;
    private final String accessKey;
    private final String clientId;
    private final String clientEmail;
    private final String bucketName;

    public GcpConfiguration() throws IOException {
        Properties properties = new Properties();
        properties.load(BiJavaGcpApplication.class.getResourceAsStream("/gcp.properties"));
        this.projectId = properties.getProperty("GCP_PROJECT_ID");
        this.accessKeyId = properties.getProperty("GCP_ACCESS_KEY_ID");
        this.accessKey = properties.getProperty("GCP_SECRET_ACCESS_KEY");
        this.clientId = properties.getProperty("GCP_CLIENT_ID");
        this.clientEmail = properties.getProperty("GCP_CLIENT_EMAIL");
        this.bucketName = properties.getProperty("GCP_BUCKET_NAME");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getBucketName() {
        return bucketName;
    }

    public Storage getStorage() throws IOException {
        GoogleCredentials credentials = ServiceAccountCredentials.fromPkcs8(
                getClientId(),
                getClientEmail(),
                getAccessKey(),
                getAccessKeyId(),
                null
        );
        return StorageOptions.newBuilder()
                .setProjectId(getProjectId())
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
