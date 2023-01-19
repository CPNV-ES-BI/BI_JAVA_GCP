package com.cpnv.BIJAVAGCP;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DataObjectTests {

/*
    @BeforeAll
    public static void test_BeforeAll() {
    }
    @BeforeEach
    public static void test_BeforeEach() {
    }
    @AfterAll
    public static void test_AfterAll() {
    }
    @AfterEach
    public static void test_AfterEach() {
    }

*/

    @Test
    public void test_DoesExist_ExistsCase_True () throws IOException {

        //given
        DataObjectController dataObjectController = new DataObjectController();
        //when
        dataObjectController.create("test.txt");
        //then
        assertTrue(dataObjectController.isExist("test.txt"));
    }
    @Test
    public void test_DoesExist_NotExists_False() {
        //given
        DataObjectController dataObjectController = new DataObjectController();
        //when
        dataObjectController.delete("test.txt");
        //then
        Assertions.assertTrue(dataObjectController.isExist("test.txt"));
    }
    @Test
    public void test_CreateObject_AlreadyExists_ThrowException() throws Exception {
        fail("This test has yet to be implement");
        //given

        //when

        //then
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
