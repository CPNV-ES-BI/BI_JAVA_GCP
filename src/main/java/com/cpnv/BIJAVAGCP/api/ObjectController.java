package com.cpnv.BIJAVAGCP.api;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class ObjectController {
    private final DataObjectController object;
    @Autowired
    public ObjectController() {
        object = new DataObjectController();
        object.setBucketName("bi.java.cld.education");
    }
    @GetMapping("/objects")
    public LinkedList<String> list() {
        return object.list();
    }
}
