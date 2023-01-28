package com.cpnv.BIJAVAGCP.Object;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class DataObjectController implements DataObject {

    public final String BUCKET_NAME = "bi.java.cld.education";
    private final Storage storage;

    public DataObjectController() {
        storage = StorageOptions.getDefaultInstance().getService();
    }
    private Blob getBlob(String fileName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        return storage.get(blobId);
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
        if (doesExist(fileName)) throw new ObjectAlreadyExistsException(fileName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }
    public void create (String fileName, String content, String path) throws ObjectAlreadyExistsException {
        BlobId blobId = BlobId.of(BUCKET_NAME, path + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        if (doesExist(path+"/"+fileName)) throw new ObjectAlreadyExistsException(fileName);
        else{
            storage.create(blobInfo, content.getBytes());
        }
    }

    public boolean doesExist(String fileName, @Nullable String... path) {
        String fullPath;
        if (path == null || path.length == 0) {
            fullPath = fileName;
        } else {
            fullPath = String.join("/", path) + "/" + fileName;
        }
        Blob blob = getBlob(fullPath);
        return blob != null;
    }


    public void delete(String fileName) throws ObjectNotExistsException {
        Blob blob = getBlob(fileName);
        if (!doesExist(fileName)) throw new ObjectNotExistsException(fileName);
        else{
            blob.delete();
        }
    }
    public void deleteRecursively(String folderName) {
        Page<Blob> blobs = storage.list(BUCKET_NAME);
        for (Blob blob : blobs.iterateAll()) {
            if (blob.getName().startsWith(folderName)) {
                storage.delete(blob.getBlobId());
            }
        }
    }
    public boolean download(String fileName, String destination) throws ObjectNotExistsException {
        Blob blob = getBlob(fileName);
        if (blob == null)  throw new ObjectNotExistsException(fileName);
        else {
            blob.downloadTo(Paths.get(destination  + fileName));
            return true;
        }
    }
    public URI publish (String fileName) throws ObjectNotExistsException {
        Blob blob = getBlob(fileName);
        if (blob == null) throw new ObjectNotExistsException(fileName);
        else {
            return URI.create(blob.signUrl(2, TimeUnit.DAYS).toString());
        }
    }
}
