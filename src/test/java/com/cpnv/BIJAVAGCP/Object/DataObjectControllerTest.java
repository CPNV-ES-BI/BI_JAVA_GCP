package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private DataObjectController dataObjectController;
    private String fileName;
    private String destination;

    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        dataObjectController = new DataObjectController();
        fileName = "test.txt";
        destination = "C:\\Users\\Robiel\\Downloads";
        dataObjectController.create(fileName);
    }
    @AfterEach
    void tearDown() {
        dataObjectController.delete(fileName);
    }
    @Test
    public void test_DoesExist_ExistsCase_True (){

        //given
        boolean expected = true;
        //when
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
    }
    @Test
    public void test_DoesExist_NotExists_False() {
        //given
        boolean expected = false;
        //when
        String fileName2 = "test2.txt";
        boolean actual = dataObjectController.isExist(fileName2);
        //then
        assertEquals(expected, actual);
    }
    @Test
    public void CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        boolean expected = true;
        //when
        dataObjectController.delete(fileName);
        dataObjectController.create(fileName);
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() {
        //given
        boolean expected = true;
        //when
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
        assertThrows(ObjectAlreadyExistsException.class, () -> dataObjectController.create(fileName));
    }
    @Test
    public void test_CreateObject_PathNotExists_Success() throws ObjectAlreadyExistsException {
        //given
        boolean expected = true;
        Path path = Paths.get("CPNV/PathNotExists/ToNoWhere");
        
        //when
        dataObjectController.create(fileName, path);
        boolean actual = dataObjectController.isExist(fileName,path);
        //then
        assertEquals(expected, actual);
        dataObjectController.delete(path+"/"+fileName);
    }
    @Test
    public void test_DownloadObject_NominalCase_Success() throws ObjectNotExistsException {
        //given
        boolean expected = true;
        //when
        boolean actual = dataObjectController.download(fileName,destination);
        //then
        assertEquals(expected,actual);
    }

    @Test
    public void test_DownloadObject_NotExists_ThrowException()  {
        //given
        String fileName2 = "test2.txt";
        //when
        //then
        assertThrows(ObjectNotExistsException.class, () -> dataObjectController.download(fileName2,destination));
    }

    @Test
    public void test_PublishObject_NominalCase_Success() throws ObjectNotExistsException {
        //given

        //when
        URI url = dataObjectController.publish(fileName);
        //then
        assertNotNull(url);
    }

    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() throws Exception {
        //given
        String fileName2 = "test2.txt";
        //when ;
        //then
        assertThrows(ObjectNotExistsException.class, () -> dataObjectController.publish(fileName2));
    }
}