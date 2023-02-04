package com.cpnv.BIJAVAGCP.Object;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String objectKey) {
        super("Object " + objectKey + " does not exist");
    }
}