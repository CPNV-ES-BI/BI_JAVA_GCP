package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class DataObjectController implements DataObject {

    public final String BUCKET_NAME = "bi.java.cld.education";
    private final Storage storage;

    public DataObjectController() {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public void list() {
        Page<Blob> blobs = storage.list(BUCKET_NAME);
        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }
    }

    public void create (String fileName, String content) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(BUCKET_NAME,  fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (isExist(fileName)) throw new ObjectAlreadyExistsException(fileName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }

    public void create (String fileName, String content, String path) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(BUCKET_NAME, path + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (isExist(path+"/"+fileName)) throw new ObjectAlreadyExistsException(fileName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }

    public boolean isExist(String fileName){
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        return blob != null;
    }

    public boolean isExist(String fileName, String path){
        BlobId blobId = BlobId.of(BUCKET_NAME, path + "/" + fileName);
        Blob blob = storage.get(blobId);
        return blob != null;
    }

    public void delete(String fileName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        storage.delete(blobId);
    }

    public boolean download(String fileName, String destination) throws ObjectNotExistsException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        if (blob == null)  throw new ObjectNotExistsException(fileName);
        else {
            blob.downloadTo(Paths.get(destination  + fileName));
            return true;
        }
    }

    public URI publish (String fileName) throws ObjectNotExistsException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        if (blob == null) throw new ObjectNotExistsException(fileName);
        else {
            return URI.create(blob.signUrl(2, TimeUnit.DAYS).toString());
        }
    }

}
