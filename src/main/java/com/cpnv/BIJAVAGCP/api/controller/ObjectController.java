package com.cpnv.BIJAVAGCP.api.controller;

import com.cpnv.BIJAVAGCP.Object.DataObjectController;
import com.cpnv.BIJAVAGCP.Object.ObjectAlreadyExistsException;
import com.cpnv.BIJAVAGCP.Object.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/api")
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
        return name + " created";
    }
    @GetMapping("/objects/{name}")
    public String getObject(@PathVariable String name) {
        if (object.doesExist(name)) return object.read(name);
        return name + " does not exist";
    }
    @DeleteMapping("/objects/{name}")
    public String deleteObject(@PathVariable String name) throws ObjectNotFoundException {
        if (object.doesExist(name)) {
            object.delete(name);
            return name + " deleted";
        } return name + " does not exist";
    }
    @GetMapping("/objects/{name}/publish")
    public String uploadObject(@PathVariable String name) throws ObjectNotFoundException {
        if (object.doesExist(name)) return String.valueOf(object.publish(name));
        return name + " does not exist";
    }
    @GetMapping("/objects/{name}/download")
    public String downloadObject(@PathVariable String name) throws ObjectNotFoundException {
        String path = "src/main/resources/";
        if (object.doesExist(name)) {
            if (object.download(name,path))return name + " downloaded";
            return name + " not downloaded";
        }  return name + " does not exist";
    }
}
