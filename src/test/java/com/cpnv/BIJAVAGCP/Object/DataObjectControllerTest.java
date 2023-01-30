package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private static DataObjectController object;
    private static String objectName, objectName2, content,destination;

    @BeforeAll
    static void setUpBeforeClass()  {
        object = new DataObjectController();
        object.setBucketName("bi.java.cld.education");
        objectName = "test.txt";
        objectName2 = "test2.txt";
        content = "Test content for object creation in GCP bucket for test purpose for BI Java course";
        destination = "src/main/resources/";
    }
    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        object.create(objectName, content);
    }
    @AfterEach
    void tearDown() throws ObjectNotFoundException {
        object.delete(objectName);
        if (object.doesExist(objectName2)) object.delete(objectName2);
    }
    @AfterAll
    static void tearDownAfterClass() throws IOException {
        Path path = Paths.get(destination + objectName);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
    @Test
    public void test_DoesExist_ExistsCase_True (){
        //given
        //when
        boolean result = object.doesExist(objectName);
        //then
        assertTrue(result);
    }
    @Test
    public void test_DoesExist_NotExists_False() {
        //given
        //when
        boolean result = object.doesExist(objectName2);
        //then
        assertFalse(result);
    }
    @Test
    public void test_CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        //when
        object.create(objectName2, content);
        boolean result = object.doesExist(objectName2);
        //then
        assertTrue(result);
    }
    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(objectName);
        //then
        assertTrue(result);
        assertThrows(ObjectAlreadyExistsException.class, () -> object.create(objectName, content));
    }
    @Test
    public void test_CreateObject_PathNotExists_Success() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        //given
        String path = "PathNotExists/ToNoWhere";
        //when
        object.create(objectName, content, path);
        boolean result = object.doesExist(objectName,path);
        //then
        assertTrue(result);
        object.delete(path+"/"+objectName);
    }
    @Test
    public void test_DownloadObject_NominalCase_Success() throws ObjectNotFoundException {
        //given
        //when
        boolean result = object.download(objectName,destination);
        //then
        assertTrue(result);
    }
    @Test
    public void test_DownloadObject_NotExists_ThrowException()  {
        //given
        //when
        boolean result = object.doesExist(objectName2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotFoundException.class, () -> object.download(objectName2,destination));
    }
    @Test
    public void test_PublishObject_NominalCase_Success() throws ObjectNotFoundException {
        //given
        URI url;
        //when
        url = object.publish(objectName);
        //then
        assertNotNull(url);
    }
    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(objectName2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotFoundException.class, () -> object.publish(objectName2));
    }
    @Test
    public void test_DeleteObject_ObjectExists_ObjectDeleted() throws ObjectNotFoundException, ObjectAlreadyExistsException {
        //given
        object.create(objectName2, content);
        //when
        object.delete(objectName2);
        boolean result = object.doesExist(objectName2);
        //then
        assertFalse(result);
    }
    @Test
    public void test_DeleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() throws ObjectAlreadyExistsException {
        //given
        String containPath = "level1/level2/level3/level4/level5";
        object.create(objectName,content,containPath);
        boolean actual = object.doesExist(objectName,containPath);
        //when
        object.delete(containPath, true);
        boolean expected = object.doesExist(objectName,containPath);
        //then
        assertNotEquals(expected, actual);
    }
    @Test
    public void test_DeleteObject_ObjectDoesntExist_ThrowException() {
        //given
        boolean result = object.doesExist(objectName2);
        //when
        //then
        assertFalse(result);
        assertThrows(ObjectNotFoundException.class, () -> object.delete(objectName2));
    }
}