package com.cpnv.BIJAVAGCP.Bucket;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Component;

@Component
public class BucketController  implements DataBucket {

    public void list() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.getName());
        }
    }
    public void create(String bucketName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        storage.create(BucketInfo.of(bucketName));
    }
    //delete bucket
    public void delete(String bucketName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        storage.delete(bucketName);
    }
}
