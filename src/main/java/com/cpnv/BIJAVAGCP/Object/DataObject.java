package com.cpnv.BIJAVAGCP.Object;

import java.net.URI;
import java.util.LinkedList;

public interface DataObject {

    LinkedList<String> list();
    void create(String objectName,String content) throws ObjectAlreadyExistsException;
    void create(String objectName,String content,String path) throws ObjectAlreadyExistsException;
    boolean doesExist(String objectName, String... path);
    void delete(String objectName) throws ObjectNotFoundException;
    void delete(String objectName, boolean isRecursive) throws ObjectNotFoundException;
    boolean download(String objectName, String destination) throws ObjectNotFoundException;
    URI publish (String objectName) throws ObjectNotFoundException;
}
