package com.cpnv.BIJAVAGCP.api.controller;

import com.cpnv.BIJAVAGCP.Object.DataObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/api")
public class DataObjectController {
    private final DataObjectService object;
    @Autowired
    public DataObjectController() {
        object = new DataObjectService();
        object.setBucketName("bi.java.cld.education");
    }
    @GetMapping("/objects")
    public LinkedList<String> getList() {
        return object.list();
    }
    @PostMapping("objects")
    public String create(@RequestParam String key, String content) throws DataObjectService.ObjectAlreadyExistsException {
        object.create(key, content);
        return key + " created";
    }
    @GetMapping("/objects/{key}")
    public String getObject(@PathVariable String key) {
        if (object.doesExist(key)) return object.read(key);
        return key + " does not exist";
    }
    @DeleteMapping("/objects/{key}")
    public String deleteObject(@PathVariable String key) throws DataObjectService.ObjectNotFoundException {
        if (object.doesExist(key)) {
            object.delete(key);
            return key + " deleted";
        } return key + " does not exist";
    }
    @PostMapping("/objects/{key}/publish")
    public String uploadObject(@PathVariable String key) throws DataObjectService.ObjectNotFoundException {
        if (object.doesExist(key)) return String.valueOf(object.publish(key));
        return key + " does not exist";
    }
    @GetMapping("/objects/{key}/download")
    public String downloadObject(@PathVariable String key) throws DataObjectService.ObjectNotFoundException {
        String path = "src/main/resources/";
        if (object.doesExist(key)) {
            if (object.download(key,path))return key + " downloaded";
            return key + " not downloaded";
        }  return key + " does not exist";
    }
}
