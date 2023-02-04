package com.cpnv.bijavagcp.exceptions;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String objectKey) {
        super("Object " + objectKey + " does not exist");
    }
}