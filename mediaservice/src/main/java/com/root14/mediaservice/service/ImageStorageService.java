package com.root14.mediaservice.service;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Autowired
    private MinioClient minioClient;

    public boolean checkIfExist(MultipartFile multipartFile) throws Exception {
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

    public ResponseEntity<?> deleteImage(String uuid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket("image").object(uuid).build());

            return ResponseEntity.ok().build();

        } catch (Exception exception) {
            //todo error handle
            throw exception;
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
            if (!Objects.equals(multipartFile.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
                ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("only accept JPEG");
            }

            if (checkIfExist(multipartFile)) {
                ResponseEntity.badRequest().body("already exist.");
            }

            UUID uuid = UUID.randomUUID();

            minioClient.putObject(PutObjectArgs.builder().bucket("image").object(uuid.toString()).stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build());

            return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
