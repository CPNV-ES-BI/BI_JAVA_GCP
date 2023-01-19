package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    private DataObjectController dataObjectController;
    private String fileName;

    @BeforeEach
    void setUp() throws IOException {
        dataObjectController = new DataObjectController();
        fileName = "test.txt";
        dataObjectController.create(fileName);
    }

    @AfterEach
    void tearDown() throws IOException {
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
}