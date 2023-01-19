package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

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

}
