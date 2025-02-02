package com.root14.mediaservice.controller;

import com.root14.mediaservice.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final ImageStorageService storageService;

    @Autowired
    public ImageController(ImageStorageService storageService) {
        this.storageService = storageService;
    }

    @PutMapping(value = "/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImage(@RequestPart("file") MultipartFile multipartFile) {
        return storageService.uploadImage(multipartFile);
    }

    @GetMapping(value = {"/getImage", "/image"})
    public ResponseEntity<?> getImage(@RequestParam String uuid) {
        return storageService.getImage(uuid);
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> removeImage(@RequestParam String uuid) {
        return storageService.deleteImage(uuid);
    }

}
