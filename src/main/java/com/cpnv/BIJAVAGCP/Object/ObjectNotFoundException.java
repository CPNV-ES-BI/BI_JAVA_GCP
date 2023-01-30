package com.cpnv.BIJAVAGCP.Object;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String objectName) {
        super("Object " + objectName + " does not exist");
    }
}