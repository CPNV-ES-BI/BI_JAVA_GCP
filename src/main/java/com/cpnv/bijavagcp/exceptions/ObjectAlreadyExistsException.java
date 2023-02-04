package com.cpnv.bijavagcp.exceptions;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String objectKey) {
        super("Object " + objectKey + " already exists");
    }
}
