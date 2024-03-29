package com.cpnv.bijavagcp.api;

import com.cpnv.bijavagcp.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavagcp.exceptions.ObjectNotFoundException;
import com.cpnv.bijavagcp.services.DataObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;

@RestController
@RequestMapping("/api")
public class DataObjectController {
    private final DataObjectService object;

    @Autowired
    public DataObjectController() throws IOException {
        object = new DataObjectService();
    }

    @GetMapping("/objects")
    public ResponseEntity<LinkedList<String>> getObjects() {
        LinkedList<String> list = object.list();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("objects")
    public ResponseEntity<String> createObject(@RequestParam String key, String content) {
        try {
            object.create(key, content);
            return new ResponseEntity<>(key + " created", HttpStatus.CREATED);
        } catch (ObjectAlreadyExistsException e) {
            return new ResponseEntity<>(key + " already exists", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("objects/upload")
    public ResponseEntity<String> uploadObject(@RequestParam String remoteFullPath, @RequestBody MultipartFile file) {
        object.upload(file, remoteFullPath);
            return new ResponseEntity<>(remoteFullPath + " uploaded", HttpStatus.CREATED);
        }

    @GetMapping("/objects/{key}")
    public ResponseEntity<String> getObject(@PathVariable String key) {
        key = key.replace("&", "/");
        if (object.doesExist(key)) {
            LinkedList<String> list = object.list(key);
            if (list.isEmpty()) {
                return new ResponseEntity<>(object.read(key), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(list.toString(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/objects/{key}")
    public ResponseEntity<String> deleteObject(@PathVariable String key)throws ObjectNotFoundException {
        if (object.doesExist(key)) {
            object.delete(key);
            return new ResponseEntity<>(key + " deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/objects/{key}/publish")
    public ResponseEntity<String> publishObject(@PathVariable String key) {
        try {
            URI result = object.publish(key);
            return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/objects/{key}/download")
    public ResponseEntity<String> downloadObject(@PathVariable String key) {
        String path = "src/main/resources/";
        try {
            byte[] result = object.download(key);
            if (result != null) {
                return new ResponseEntity<>(key + " downloaded", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(key + " not downloaded", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(key + " does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
