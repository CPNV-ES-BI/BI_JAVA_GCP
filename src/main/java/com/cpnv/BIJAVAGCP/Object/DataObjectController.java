package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DataObjectController implements DataObject {

    private String bucketName;
    private final Storage storage;

    public DataObjectController() {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    private Blob getBlob(String objectName) {
        BlobId blobId = BlobId.of(bucketName, objectName);
        return storage.get(blobId);
    }
    public LinkedList<String> list() {
        Page<Blob> blobs = storage.list(bucketName);
        LinkedList<String> list = new LinkedList<>();
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getName());
            System.out.println(blob.getName());
        }
        return list;
    }
    public void create (String objectName, String content) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName,  objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(objectName)) throw new ObjectAlreadyExistsException(objectName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }
    public void create (String objectName, String content, String path) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName, path + "/" + objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(path+"/"+objectName)) throw new ObjectAlreadyExistsException(objectName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }
    public boolean doesExist(String objectName, @Nullable String... path) {
        String fullPath;
        if (path == null || path.length == 0) {
            fullPath = objectName;
        } else {
            fullPath = String.join("/", path) + "/" + objectName;
        }
        Blob blob = getBlob(fullPath);
        return blob != null;
    }
    public void delete(String objectName) throws ObjectNotFoundException {
        Blob blob = getBlob(objectName);
        if (!doesExist(objectName)) throw new ObjectNotFoundException(objectName);
        else{
            blob.delete();
        }
    }
    public void delete(String folderName,boolean isRecursive) {
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(folderName));
        if (isRecursive) {
            for (Blob blob : blobs.iterateAll()) {
                blob.delete();
            }
        } else {
           for (Blob blob : blobs.iterateAll()) {
                if (blob.getName().equals(folderName)) {
                    blob.delete();
                }
            }
        }
    }
    public boolean download(String objectName, String destination) throws ObjectNotFoundException {
        Blob blob = getBlob(objectName);
        if (blob == null)  throw new ObjectNotFoundException(objectName);
        else {
            blob.downloadTo(Paths.get(destination  + objectName));
            return true;
        }
    }
    public URI publish (String objectName) throws ObjectNotFoundException {
        Blob blob = getBlob(objectName);
        if (blob == null) throw new ObjectNotFoundException(objectName);
        else {
            return URI.create(blob.signUrl(2, TimeUnit.DAYS).toString());
        }
    }
}
