package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private static DataObjectController dataObjectController;
    private static String fileName, fileName2, content,destination;

    @BeforeAll
    static void setUpBeforeClass()  {
        dataObjectController = new DataObjectController();
        fileName = "test.txt";
        fileName2 = "test2.txt";
        content = "Test content for object creation in GCP bucket for test purpose for BI Java course";
        destination = "src/main/resources/";
    }
    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        dataObjectController.create(fileName, content);
    }
    @AfterEach
    void tearDown() throws ObjectNotExistsException {
        dataObjectController.delete(fileName);
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
        fileName2 = "test2.txt";
        //when
        boolean actual = dataObjectController.isExist(fileName2);
        //then
        assertEquals(expected, actual);
    }
    @Test
    public void test_CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        boolean expected = true;
        //when
        dataObjectController.create(fileName2, content);
        boolean actual = dataObjectController.isExist(fileName2);
        dataObjectController.delete(fileName2);
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
        assertThrows(ObjectAlreadyExistsException.class, () -> dataObjectController.create(fileName, content));
    }
    @Test
    public void test_CreateObject_PathNotExists_Success() throws ObjectAlreadyExistsException, ObjectNotExistsException {
        //given
        boolean expected = true;
        String path = "PathNotExists/ToNoWhere";

        //when
        dataObjectController.create(fileName, content, path);
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
        fileName2 = "test2.txt";
        //when
        assertFalse(dataObjectController.isExist(fileName2));
        //then
        assertThrows(ObjectNotExistsException.class, () -> dataObjectController.download(fileName2,destination));
    }
    @Test
    public void test_PublishObject_NominalCase_Success() throws ObjectNotExistsException {
        //given
        URI url;
        //when
        url = dataObjectController.publish(fileName);
        //then
        assertNotNull(url);
    }
    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() {
        //given
        fileName2 = "test2.txt";
        //when
        assertFalse(dataObjectController.isExist(fileName2));
        //then
        assertThrows(ObjectNotExistsException.class, () -> dataObjectController.publish(fileName2));
    }
    @Test
    public void test_DeleteObject_ObjectExists_ObjectDeleted() throws ObjectNotExistsException, ObjectAlreadyExistsException {
        //given
        dataObjectController.create(fileName2, content);
        boolean actual = dataObjectController.isExist(fileName2);
        //when
        dataObjectController.delete(fileName2);
        boolean expected = dataObjectController.isExist(fileName2);
        //then
        assertNotEquals(expected, actual);
    }
    @Test
    public void test_DeleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() throws ObjectAlreadyExistsException {

        //given
        String containPath = "level1/level2/level3/level4/level5";
        dataObjectController.create(fileName,content,containPath);
        dataObjectController.create(fileName2,content,containPath);
        boolean actual1 = dataObjectController.isExist(fileName2,containPath);
        boolean actual2 = dataObjectController.isExist(fileName,containPath);

        //when
        dataObjectController.deleteRecursively(containPath);
        boolean expected1 = dataObjectController.isExist(fileName2,containPath);
        boolean expected2 = dataObjectController.isExist(fileName,containPath);

        //then
        assertNotEquals(expected1, actual1);
        assertNotEquals(expected2, actual2);
    }

    @Test
    public void test_DeleteObject_ObjectDoesntExist_ThrowException() throws ObjectNotExistsException {
        //given
        boolean expected = false;
        //when
        boolean actual = dataObjectController.isExist(fileName2);
        //then
        assertEquals(expected, actual);
        assertThrows(ObjectNotExistsException.class, () -> dataObjectController.delete(fileName2));
    }

}