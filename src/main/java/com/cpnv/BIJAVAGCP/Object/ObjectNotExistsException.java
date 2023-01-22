package com.cpnv.BIJAVAGCP.Object;

public class ObjectNotExistsException extends Exception {
    public ObjectNotExistsException(String fileName) {
        super("Object " + fileName + " does not exist");
    }
}