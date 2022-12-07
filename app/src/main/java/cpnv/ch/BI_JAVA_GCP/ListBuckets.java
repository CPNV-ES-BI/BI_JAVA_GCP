package cpnv.ch.BI_JAVA_GCP;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.util.LinkedList;

public class ListBuckets {
    public static LinkedList listBuckets(String projectId) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Bucket> buckets = storage.list();
        LinkedList listBuckes = null;

        for (Bucket bucket : buckets.iterateAll()) {
            listBuckes.add(bucket.getName());
            System.out.println(bucket.getName());
        }
        return listBuckes;
    }
}
