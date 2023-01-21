package com.cpnv.BIJAVAGCP.Object;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String fileName) {
        super("Object " + fileName + " already exists");
    }
}
