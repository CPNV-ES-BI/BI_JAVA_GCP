package com.cpnv.BIJAVAGCP.api.controller;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import com.cpnv.BIJAVAGCP.Object.ObjectAlreadyExistsException;
import com.cpnv.BIJAVAGCP.Object.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
public class ObjectController {
    private final DataObjectController object;
    @Autowired
    public ObjectController() {
        object = new DataObjectController();
        object.setBucketName("bi.java.cld.education");
    }
    @GetMapping("/objects") //index
    public LinkedList<String> getList() {
        return object.list();
    }
    @PostMapping("objects")
    public String create(@RequestParam String name, String content) throws ObjectAlreadyExistsException {
        object.create(name, content);
        object.list();
        return name + " created";
    }
    @GetMapping("/objects/")
    public String getObject(@RequestParam String name) {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            return name + " exists";
        } else {
            return name + " does not exist";
        }
    }

    @DeleteMapping("/objects/")
    public String deleteObject(@RequestParam String name) throws ObjectNotFoundException {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            object.delete(name);
            return name + " deleted";
        } else {
            return name + " does not exist";
        }
    }

    @GetMapping("/objects/publish/")
    public String uploadObject(@RequestParam String name) throws ObjectNotFoundException {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            object.publish(name);
            return String.valueOf(object.publish(name));
        } else {
            return name + " does not exist";
        }
    }
}
