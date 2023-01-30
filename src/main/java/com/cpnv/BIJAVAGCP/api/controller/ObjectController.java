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
    @GetMapping("/objects")
    public LinkedList<String> getList() {
        return object.list();
    }
    @PostMapping("objects")
    public String create(@RequestParam String name, String content) throws ObjectAlreadyExistsException {
        object.create(name, content);
        object.list();
        return name + " created";
    }
    @GetMapping("/objects/{name}")
    public String getObject(@PathVariable String name) {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            return name + " exists";
        } else {
            return name + " does not exist";
        }
    }
    @DeleteMapping("/objects/{name}")
    public String deleteObject(@PathVariable String name) throws ObjectNotFoundException {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            object.delete(name);
            return name + " deleted";
        } else {
            return name + " does not exist";
        }
    }
    @GetMapping("/objects/{name}/publish")
    public String uploadObject(@PathVariable String name) throws ObjectNotFoundException {
        boolean doesExist = object.doesExist(name);
        if (doesExist) {
            object.publish(name);
            return String.valueOf(object.publish(name));
        } else {
            return name + " does not exist";
        }
    }
    @GetMapping("/objects/{name}/download")
    public String downloadObject(@PathVariable String name) throws ObjectNotFoundException {
        boolean doesExist = object.doesExist(name);
        String path = "src/main/resources/";
        if (doesExist) {
            object.download(name,path);
            if (object.download(name,path)) {
                return name + " downloaded";
            } else {
                return name + " not downloaded";
            }
        } else {
            return name + " does not exist";
        }
    }
}
