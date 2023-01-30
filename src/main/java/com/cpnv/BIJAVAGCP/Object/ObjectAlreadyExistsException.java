package com.cpnv.BIJAVAGCP.Object;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String objectName) {
        super("Object " + objectName + " already exists");
    }
}
