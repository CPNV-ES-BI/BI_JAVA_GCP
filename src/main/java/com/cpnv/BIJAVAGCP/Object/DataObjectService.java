package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DataObjectService implements DataObject {

    private String bucketName;
    private final Storage storage;

    public DataObjectService() {
        storage = StorageOptions.getDefaultInstance().getService();
    }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    private Blob getBlob(String objectKey) {
        BlobId blobId = BlobId.of(bucketName, objectKey);
        return storage.get(blobId);
    }
    public LinkedList<String> list() {
        Page<Blob> blobs = storage.list(bucketName);
        LinkedList<String> list = new LinkedList<>();
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getName());
        }
        return list;
    }
    public void create (String objectKey, String content) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName,  objectKey);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(objectKey)) throw new ObjectAlreadyExistsException(objectKey);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }
    public void create (String objectKey, String content, String path) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName, path + "/" + objectKey);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(path+"/"+objectKey)) throw new ObjectAlreadyExistsException(objectKey);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }
    public boolean doesExist(String objectKey, @Nullable String... path) {
        String fullPath;
        if (path == null || path.length == 0) {
            fullPath = objectKey;
        } else {
            fullPath = String.join("/", path) + "/" + objectKey;
        }
        Blob blob = getBlob(fullPath);
        return blob != null;
    }
    public void delete(String objectKey) throws ObjectNotFoundException {
        Blob blob = getBlob(objectKey);
        if (!doesExist(objectKey)) throw new ObjectNotFoundException(objectKey);
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
    public boolean download(String objectKey, String path) throws ObjectNotFoundException {
        Blob blob = getBlob(objectKey);
        if (blob == null)  throw new ObjectNotFoundException(objectKey);
        else {
            blob.downloadTo(Paths.get(path  + objectKey));
            return true;
        }
    }
    public URI publish (String objectKey) throws ObjectNotFoundException {
        Blob blob = getBlob(objectKey);
        if (blob == null) throw new ObjectNotFoundException(objectKey);
        else {
            return URI.create(blob.signUrl(2, TimeUnit.DAYS).toString());
        }
    }

    public String read(String name) {
        Blob blob = getBlob(name);
        return new String(blob.getContent());
    }

    public static class ObjectNotFoundException extends Exception {
        public ObjectNotFoundException(String objectKey) {
            super("Object " + objectKey + " not found");
        }
    }

    public static class ObjectAlreadyExistsException extends Exception {
        public ObjectAlreadyExistsException(String objectKey) {
            super("Object " + objectKey + " already exists");
        }
    }
}
