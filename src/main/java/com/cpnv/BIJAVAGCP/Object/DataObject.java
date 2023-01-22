package com.cpnv.BIJAVAGCP.Object;

import java.net.URI;

public interface DataObject {

    void list();
    void create(String fileName,String content) throws ObjectAlreadyExistsException;
    void create(String fileName,String content,String path) throws ObjectAlreadyExistsException;
    boolean isExist(String fileName);
    boolean isExist(String fileName, String path);
    void delete(String fileName) throws ObjectNotExistsException;
    void deleteRecursively(String path) throws ObjectNotExistsException;
    boolean download(String fileName, String  destination) throws ObjectNotExistsException;
    URI publish (String fileName) throws ObjectNotExistsException;
}
