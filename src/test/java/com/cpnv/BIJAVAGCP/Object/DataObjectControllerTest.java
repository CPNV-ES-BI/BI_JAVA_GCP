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
    private static String fileName, fileName2, content,destination;

    @BeforeAll
    static void setUpBeforeClass()  {
        object = new DataObjectController();
        fileName = "test.txt";
        fileName2 = "test2.txt";
        content = "Test content for object creation in GCP bucket for test purpose for BI Java course";
        destination = "src/main/resources/";
    }
    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        object.create(fileName, content);
    }
    @AfterEach
    void tearDown() throws ObjectNotExistsException {
        object.delete(fileName);
    }
    @AfterAll
    static void tearDownAfterClass() throws IOException {
        Path path = Paths.get(destination + fileName);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
    @Test
    public void test_DoesExist_ExistsCase_True (){
        //given
        //when
        boolean result = object.doesExist(fileName);
        //then
        assertTrue(result);
    }
    @Test
    public void test_DoesExist_NotExists_False() {
        //given
        fileName2 = "test2.txt";
        //when
        boolean result = object.doesExist(fileName2);
        //then
        assertFalse(result);
    }
    @Test
    public void test_CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        //when
        object.create(fileName2, content);
        boolean result = object.doesExist(fileName2);
        object.delete(fileName2);
        //then
        assertTrue(result);
    }
    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(fileName);
        //then
        assertTrue(result);
        assertThrows(ObjectAlreadyExistsException.class, () -> object.create(fileName, content));
    }
    @Test
    public void test_CreateObject_PathNotExists_Success() throws ObjectAlreadyExistsException, ObjectNotExistsException {
        //given
        String path = "PathNotExists/ToNoWhere";
        //when
        object.create(fileName, content, path);
        boolean result = object.doesExist(fileName,path);
        //then
        assertTrue(result);
        object.delete(path+"/"+fileName);
    }
    @Test
    public void test_DownloadObject_NominalCase_Success() throws ObjectNotExistsException {
        //given
        //when
        boolean result = object.download(fileName,destination);
        //then
        assertTrue(result);
    }
    @Test
    public void test_DownloadObject_NotExists_ThrowException()  {
        //given
        fileName2 = "test2.txt";
        //when
        boolean result = object.doesExist(fileName2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotExistsException.class, () -> object.download(fileName2,destination));
    }
    @Test
    public void test_PublishObject_NominalCase_Success() throws ObjectNotExistsException {
        //given
        URI url;
        //when
        url = object.publish(fileName);
        //then
        assertNotNull(url);
    }
    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() {
        //given
        fileName2 = "test2.txt";
        //when
        boolean result = object.doesExist(fileName2);
        assertFalse(result);
        //then
        assertThrows(ObjectNotExistsException.class, () -> object.publish(fileName2));
    }
    @Test
    public void test_DeleteObject_ObjectExists_ObjectDeleted() throws ObjectNotExistsException, ObjectAlreadyExistsException {
        //given
        object.create(fileName2, content);
        boolean actual = object.doesExist(fileName2);
        //when
        object.delete(fileName2);
        boolean expected = object.doesExist(fileName2);
        //then
        assertNotEquals(expected, actual);
    }
    @Test
    public void test_DeleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() throws ObjectAlreadyExistsException {
        //given
        String containPath = "level1/level2/level3/level4/level5";
        object.create(fileName,content,containPath);
        object.create(fileName2,content,containPath);
        boolean actual1 = object.doesExist(fileName2,containPath);
        boolean actual2 = object.doesExist(fileName,containPath);
        //when
        object.deleteRecursively(containPath);
        boolean expected1 = object.doesExist(fileName2,containPath);
        boolean expected2 = object.doesExist(fileName,containPath);
        //then
        assertNotEquals(expected1, actual1);
        assertNotEquals(expected2, actual2);
    }
    @Test
    public void test_DeleteObject_ObjectDoesntExist_ThrowException() {
        //given
        //when
        boolean result = object.doesExist(fileName2);
        //then
        assertFalse(result);
        assertThrows(ObjectNotExistsException.class, () -> object.delete(fileName2));
    }
}