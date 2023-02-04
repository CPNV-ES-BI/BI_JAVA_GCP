package com.cpnv.bijavagcp.services;

import java.net.URI;
import java.util.LinkedList;

public interface DataObject {

    LinkedList<String> list();
    void create(String objectKey,String content) throws DataObjectService.ObjectAlreadyExistsException;
    void create(String objectKey,String content,String path) throws DataObjectService.ObjectAlreadyExistsException;
    boolean doesExist(String objectKey, String... path);
    void delete(String objectKey) throws DataObjectService.ObjectNotFoundException;
    void delete(String objectKey, boolean isRecursive) throws DataObjectService.ObjectNotFoundException;
    boolean download(String objectKey, String destination) throws DataObjectService.ObjectNotFoundException;
    URI publish (String objectKey) throws DataObjectService.ObjectNotFoundException;
}
