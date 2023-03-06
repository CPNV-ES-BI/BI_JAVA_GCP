package com.cpnv.bijavagcp.services;

import com.cpnv.bijavagcp.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavagcp.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private static DataObjectService object;
    private static String objectKey, objectKey2, content,destination;

    @BeforeAll
    static void setUpBeforeClass() throws IOException {
        object = new DataObjectService();
        objectKey = "test.txt";
        objectKey2 = "test2.txt";
        content = "Test content for object creation in GCP bucket for test purpose for BI Java course";
        destination = "src/main/resources/";
    }

    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        object.create(objectKey, content);
    }

    @AfterEach
    void tearDown() throws ObjectNotFoundException {
        object.delete(objectKey);
        if (object.doesExist(objectKey2)) object.delete(objectKey2);
    }

    @AfterAll
    static void tearDownAfterClass() throws IOException {
        Path path = Paths.get(destination + objectKey);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    public void test_DoesExist_ExistsCase_True (){
        //given
        //when
        boolean result = object.doesExist(objectKey);
        //then
        assertTrue(result);
    }

    @Test
    public void test_DoesExist_NotExists_False() {
        //given
        //when
        boolean result = object.doesExist(objectKey2);
        //then
        assertFalse(result);
    }

    @Test
    public void test_CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        //when
        object.create(objectKey2, content);
        boolean result = object.doesExist(objectKey2);
        //then
        assertTrue(result);
    }

    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(objectKey);
        //then
        assertTrue(result);
        assertThrows(ObjectAlreadyExistsException.class, () -> object.create(objectKey, content));
    }

    @Test
    public void test_CreateObject_PathNotExists_Success() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        //given
        String path = "PathNotExists/ToNoWhere";
        //when
        object.create(objectKey, content, path);
        boolean result = object.doesExist(objectKey,path);
        //then
        assertTrue(result);
        object.delete(path+"/"+objectKey);
    }

    @Test
    public void test_DownloadObject_NominalCase_Success() throws ObjectNotFoundException {
        //given
        //when
        byte[] result = object.download(objectKey);
        //then
        assertNotNull(result);
    }

    @Test
    public void test_DownloadObject_NotExists_ThrowException()  {
        //given
        //when
        boolean result = object.doesExist(objectKey2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotFoundException.class, () -> object.download(objectKey2));
    }

    @Test
    public void test_PublishObject_NominalCase_Success() throws ObjectNotFoundException {
        //given
        URI url;
        //when
        url = object.publish(objectKey);
        //then
        assertNotNull(url);
    }

    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(objectKey2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotFoundException.class, () -> object.publish(objectKey2));
    }

    @Test
    public void test_DeleteObject_ObjectExists_ObjectDeleted() throws ObjectNotFoundException, ObjectAlreadyExistsException {
        //given
        object.create(objectKey2, content);
        //when
        object.delete(objectKey2);
        boolean result = object.doesExist(objectKey2);
        //then
        assertFalse(result);
    }

    @Test
    public void test_DeleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() throws ObjectAlreadyExistsException {
        //given
        String containPath = "level1/level2/level3/level4/level5";
        object.create(objectKey,content,containPath);
        boolean actual = object.doesExist(objectKey,containPath);
        //when
        object.delete(containPath, true);
        boolean expected = object.doesExist(objectKey,containPath);
        //then
        assertNotEquals(expected, actual);
    }

    @Test
    public void test_DeleteObject_ObjectDoesntExist_ThrowException() {
        //given
        boolean result = object.doesExist(objectKey2);
        //when
        //then
        assertFalse(result);
        assertThrows(ObjectNotFoundException.class, () -> object.delete(objectKey2));
    }
}