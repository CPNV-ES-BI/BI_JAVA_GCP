package com.cpnv.BIJAVAGCP;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataObjectController implements DataObject {

    // The ID of your GCP project
    // String projectId = "your-project-id";
    public String projectId = "es-bi-370207";
    public Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();


    /**
     * Create a bucket with a specific name
     * @param bucketName the name of the bucket
     */
    public void createObject(String bucketName){

        // Creates the new bucket
        Bucket bucket = storage.create(BucketInfo.of(bucketName));

        System.out.printf("Bucket %s created.%n", bucket.getName());
    }

    /**
     * Delete a bucket with a specific name
     * @param bucketName the name of the bucket
     * @param objectName the name of the object
     */
    public void deleteObject(String bucketName, String objectName) {

        Blob blob = storage.get(bucketName, objectName);
        if (blob == null) {
            System.out.println("The object " + objectName + " wasn't found in " + bucketName);
            return;
        }

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request to upload returns a 412 error if
        // the object's generation number does not match your precondition.
        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(bucketName, objectName, precondition);

        System.out.println("Object " + objectName + " was deleted from " + bucketName);
    }

    /**
     * List all the objects in a bucket
     * @param bucketName the name of the bucket
     */
    public void listObjects(String bucketName) {

        Page<Blob> blobs = storage.list(bucketName);

        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }
    }

    /**
     * Download an object from a bucket
     * @param bucketName the name of the bucket
     * @param objectName the name of the object
     * @param destFilePath the path where the object will be downloaded
     */
    public String downloadObject(String bucketName, String objectName, String destFilePath) {

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);
        return "";
    }

    /**
     * Publish an object in a bucket
     * @param bucketName the name of the bucket
     * @param objectName the name of the object
     * @param filePath the path of the object
     */
    public void publishObject(String bucketName, String objectName, String filePath) throws IOException {

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request returns a 412 error if the
        // preconditions are not met.
        Storage.BlobTargetOption precondition;
        if (storage.get(bucketName, objectName) == null) {
            // For a target object that does not yet exist, set the DoesNotExist precondition.
            // This will cause the request to fail if the object is created before the request runs.
            precondition = Storage.BlobTargetOption.doesNotExist();
        } else {
            // If the destination already exists in your bucket, instead set a generation-match
            // precondition. This will cause the request to fail if the existing object's generation
            // changes before the request runs.
            precondition =
                    Storage.BlobTargetOption.generationMatch(
                    );
        }
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)), precondition);

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }
}
