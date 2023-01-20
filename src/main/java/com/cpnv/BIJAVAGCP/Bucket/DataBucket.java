package com.cpnv.BIJAVAGCP.Bucket;


public interface DataBucket {

     void create(String bucketName);
     void delete(String bucketName);
     void list();

}
