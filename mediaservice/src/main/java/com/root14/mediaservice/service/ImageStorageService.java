package com.root14.mediaservice.service;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class ImageStorageService {


    private final MinioClient minioClient;

    @Autowired
    public ImageStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;


        checkIfBucketExistNCreate();
    }

    private void checkIfBucketExistNCreate() {
        //todo create bucket
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("image").build());
        } catch (Exception e) {
            //already created or exception
        }
    }

    private boolean checkIfExist(MultipartFile multipartFile) throws Exception {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket("image").object(multipartFile.getName()).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<?> getImage(String uuid) {
        try {
            InputStream object = minioClient.getObject(GetObjectArgs.builder().bucket("image").object(uuid).build());

            byte[] imageBytes = object.readAllBytes();

            String contentType = "image/jpeg";

            return ResponseEntity.ok().contentType(MediaType.valueOf(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + uuid + "\"").body(imageBytes);
        } catch (Exception exception) {
            //todo error handle
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
        }
    }

    public ResponseEntity<?> deleteImage(String uuid) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket("image").object(uuid).build());

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            //todo error handle
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Uploads an image file to MinIO.
     *
     * @param multipartFile A multipart, typically used during image upload processes in web applications.
     * @return ResponseEntity<?> The response entity containing the UUID of the uploaded object if successful, or an error message otherwise.
     */
    public ResponseEntity<?> uploadImage(MultipartFile multipartFile) {
        try {

            List<String> supportedFormats = new ArrayList<>();
            supportedFormats.add(MediaType.IMAGE_JPEG_VALUE);
            supportedFormats.add(MediaType.IMAGE_PNG_VALUE);

            if (!supportedFormats.contains(multipartFile.getContentType())) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("only accept JPEG,JPG,PNG");
            }

            if (checkIfExist(multipartFile)) {
                ResponseEntity.badRequest().body("already exist.");
            }

            UUID uuid = UUID.randomUUID();

            minioClient.putObject(PutObjectArgs.builder().bucket("image").object(uuid.toString()).stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build());
            Map<String, String> response = new HashMap<>();
            response.put("uuid", uuid.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
