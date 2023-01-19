package com.cpnv.BIJAVAGCP.Object;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataObjectControllerTest {

    //create a test for @BeforeEach



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
        assertFalse(dataObjectController.isExist("test.txt"));
    }
}