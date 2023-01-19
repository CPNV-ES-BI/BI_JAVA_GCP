package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private DataObjectController dataObjectController;
    private String fileName;

    @BeforeEach
    void setUp() throws ObjectAlreadyExistsException {
        dataObjectController = new DataObjectController();
        fileName = "test.txt";
        dataObjectController.create(fileName);
    }
    @AfterEach
    void tearDown() {
        dataObjectController.delete(fileName);
    }
    @Test
    public void test_DoesExist_ExistsCase_True () throws IOException {

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
        fileName = "test2.txt";
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void CreateObject_NominalCase_ObjectExists() throws Exception {
        //given
        boolean expected = true;
        //when
        fileName = "test2.txt";
        dataObjectController.create(fileName);
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
        dataObjectController.delete(fileName);
    }
    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() {
        //given
        boolean expected = true;
        //when
        boolean actual = dataObjectController.isExist(fileName);
        //then
        assertEquals(expected, actual);
        assertThrows(ObjectAlreadyExistsException.class, () -> {
            dataObjectController.create(fileName);
        });
    }
    @Test
    public void test_CreateObject_PathNotExists_Success() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
    }

    @Test
    public void test_DownloadObject_NominalCase_Success() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
    }

    @Test
    public void test_DownloadObject_NotExists_ThrowException() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
    }

    @Test
    public void test_PublishObject_NominalCase_Success() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
    }

    @Test
    public void test_PublishObject_ObjectNotFound_ThrowException() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
    }
}