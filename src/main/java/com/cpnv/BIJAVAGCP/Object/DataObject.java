package com.cpnv.BIJAVAGCP.Object;

import java.net.URI;

public interface DataObject {

    void list();
    void create(String fileName,String content) throws ObjectAlreadyExistsException;
    boolean isExist(String fileName);
    void delete(String fileName) throws ObjectNotExistsException;
    boolean download(String fileName, String  destination) throws ObjectNotExistsException;
    URI publish (String fileName) throws ObjectNotExistsException;
}
