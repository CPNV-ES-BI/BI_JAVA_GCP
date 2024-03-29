package com.cpnv.bijavagcp.services;

import com.cpnv.bijavagcp.config.GcpConfiguration;
import com.cpnv.bijavagcp.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavagcp.exceptions.ObjectNotFoundException;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DataObjectService implements DataObject {

    private final String bucketName;
    private final Storage storage;

    public DataObjectService() throws IOException {
        GcpConfiguration gcp = new GcpConfiguration();
        storage = gcp.getStorage();
        bucketName = gcp.getBucketName();
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

    public LinkedList<String> list(String path) {
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(path+"/"));
        LinkedList<String> list = new LinkedList<>();
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getName().replace(path + "/", ""));
        }
        return list;
    }

    public void create(String objectKey, String content) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName, objectKey);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(objectKey)) throw new ObjectAlreadyExistsException(objectKey);
        else {
            storage.create(blobInfo, content.getBytes());
        }
    }

    public void create(String objectKey, String content, String path) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(bucketName, path + "/" + objectKey);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(path + "/" + objectKey)) throw new ObjectAlreadyExistsException(objectKey);
        else {
            storage.create(blobInfo, content.getBytes());
        }
    }
    public void upload(MultipartFile file,String remoteFullPath){
        BlobId blobId = BlobId.of(bucketName, remoteFullPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean doesExist(String objectKey) {
        Blob blob = getBlob(objectKey);
        return blob != null;
    }

    public boolean doesExist(String objectKey, String path) {
        Blob blob = getBlob(path + "/" + objectKey);
        return blob != null;
    }

    public void delete(String objectKey) throws ObjectNotFoundException {
        Blob blob = getBlob(objectKey);
        if (!doesExist(objectKey)) throw new ObjectNotFoundException(objectKey);
        else {
            blob.delete();
        }
    }

    public void delete(String objectKey, boolean isRecursive) {
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(objectKey + "/"));
        if (isRecursive) {
            for (Blob blob : blobs.iterateAll()) {
                blob.delete();
            }
        } else {
            for (Blob blob : blobs.iterateAll()) {
                if (blob.getName().equals(objectKey)) {
                    blob.delete();
                }
            }
        }
    }

     public byte[] download(String objectKey) throws ObjectNotFoundException {
        Blob blob = getBlob(objectKey);
        if (blob == null) throw new ObjectNotFoundException(objectKey);
        else {
            return blob.getContent();
        }
     }

    public URI publish(String remoteFullPath) throws ObjectNotFoundException {
        Blob blob = getBlob(remoteFullPath);
        if (blob == null) throw new ObjectNotFoundException(remoteFullPath);
        else {
            return URI.create(blob.signUrl(90, TimeUnit. SECONDS).toString());
        }
    }

    public URI publish(String remoteFullPath,int expirationTime) throws ObjectNotFoundException {
        Blob blob = getBlob(remoteFullPath);
        if (blob == null) throw new ObjectNotFoundException(remoteFullPath);
        else {
            return URI.create(blob.signUrl(expirationTime, TimeUnit.SECONDS).toString());
        }
    }


    public String read(String objectKey) {
        Blob blob = getBlob(objectKey);
        return new String(blob.getContent());
    }
}
