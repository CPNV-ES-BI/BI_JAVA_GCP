package com.cpnv.bijavagcp.services;

import com.cpnv.bijavagcp.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavagcp.exceptions.ObjectNotFoundException;

import java.net.URI;
import java.util.LinkedList;

public interface DataObject {

    LinkedList<String> list();
    
    LinkedList<String> list(String path);

    void create(String objectKey, String content) throws ObjectAlreadyExistsException;

    void create(String objectKey, String content, String path) throws ObjectAlreadyExistsException;

    boolean doesExist(String objectKey, String... path);

    void delete(String objectKey) throws ObjectNotFoundException;

    void delete(String objectKey, boolean isRecursive) throws ObjectNotFoundException;

    byte[] download(String objectKey) throws ObjectNotFoundException;

    URI publish(String objectKey) throws ObjectNotFoundException;
}
