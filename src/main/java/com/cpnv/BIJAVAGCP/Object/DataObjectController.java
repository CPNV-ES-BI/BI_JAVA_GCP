package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.nio.file.Paths;

public class DataObjectController implements DataObject {

    public static final String BUCKET_NAME = "bi.java.cld.education";

    public void list() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Page<Blob> blobs = storage.list(BUCKET_NAME);
        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }
    }

    public void create(String fileName) throws ObjectAlreadyExistsException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (isExist(fileName)){
           System.out.println(  "Blob " + fileName + " already exists.");
            throw new ObjectAlreadyExistsException("object already exist");
        }else{
            Blob blob = storage.create(blobInfo, "Hello, World!".getBytes());
            System.out.println("Blob " + blob.getName() + " was created: " + blob.getCreateTime());
        }
    }
    public void create(String fileName, String path) throws ObjectAlreadyExistsException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, path + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (isExist(path+"/"+fileName)){
           System.out.println(  "Blob " + fileName + " already exists.");
            throw new ObjectAlreadyExistsException("object already exist");
        }else{
            Blob blob = storage.create(blobInfo, "Hello, World!".getBytes());
            System.out.println("Blob " + blob.getName() + " was created: " + blob.getCreateTime());
        }
    }

    public void read(String fileName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("Blob " + fileName + " does not exist.");
        } else {
            System.out.println(new String(blob.getContent()));
        }
    }

    public boolean isExist(String fileName){
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("Blob " + blobId.getName() + " does not exist.");
            return false;
        } else {
            System.out.println("Blob " + blob.getName() + " exists.");
            return true;
        }
    }

    public boolean isExist(String fileName, String path){
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, path + "/" + fileName);
        Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("Blob " + blobId.getName() + " does not exist.");
            return false;
        } else {
            System.out.println("Blob " + blob.getName() + " exists.");
            return true;
        }
    }
    
    public void delete(String fileName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            System.out.println("Blob " + blobId.getName() + " was deleted.");
        } else {
            System.out.println("Blob " + blobId.getName() + " was not found.");
        }
    }

    public boolean download(String fileName, String destination) throws ObjectNotExistsException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("Blob " + fileName + " does not exist.");
            throw new ObjectNotExistsException("object does not exist");
        } else {
            blob.downloadTo(Paths.get(destination  + fileName));
            System.out.println("Blob " + fileName + " was downloaded.");
            return true;
        }
    }

}
