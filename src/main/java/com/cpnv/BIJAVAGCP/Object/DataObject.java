package com.cpnv.BIJAVAGCP.Object;

public interface DataObject {

    void list();
    void create(String fileName) throws ObjectAlreadyExistsException;
    boolean isExist(String fileName);
    void delete(String fileName);
}
