package com.cpnv.bijavagcp.api.controller;

import com.cpnv.bijavagcp.services.DataObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<LinkedList<String>> getList() {
        LinkedList<String> list = object.list();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("objects")
    public ResponseEntity<String> create(@RequestParam String key, String content) {
        try {
            object.create(key, content);
            return new ResponseEntity<>(key + " created", HttpStatus.CREATED);
        } catch (DataObjectService.ObjectAlreadyExistsException e) {
            return new ResponseEntity<>(key + " already exists", HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/objects/{key}")
    public ResponseEntity<String> getObject(@PathVariable String key) {
        if (object.doesExist(key)) {
            return new ResponseEntity<>(object.read(key), HttpStatus.OK);
        }
        return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/objects/{key}")
    public ResponseEntity<String> deleteObject(@PathVariable String key) {
        try {
            object.delete(key);
            return new ResponseEntity<>(key + " deleted", HttpStatus.OK);
        } catch (DataObjectService.ObjectNotFoundException e) {
            return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/objects/{key}/publish")
    public ResponseEntity<String> uploadObject(@PathVariable String key) {
        try {
            URI result = object.publish(key);
            return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
        } catch (DataObjectService.ObjectNotFoundException e) {
            return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/objects/{key}/download")
    public ResponseEntity<String> downloadObject(@PathVariable String key) {
        String path = "src/main/resources/";
        try {
            boolean result = object.download(key,path);
            if (result) {
                return new ResponseEntity<>(key + " downloaded", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(key + " not downloaded", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataObjectService.ObjectNotFoundException e) {
            return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
