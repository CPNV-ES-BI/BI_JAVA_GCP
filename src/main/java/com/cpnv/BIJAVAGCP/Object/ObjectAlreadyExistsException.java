package com.cpnv.BIJAVAGCP.Object;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String objectKey) {
        super("Object " + objectKey + " already exists");
    }
}
