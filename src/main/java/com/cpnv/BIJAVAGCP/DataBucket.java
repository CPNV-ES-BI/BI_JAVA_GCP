package com.cpnv.BIJAVAGCP;


public interface DataBucket {

     void create(String bucketName);
     void delete(String bucketName);
     void list();

}
