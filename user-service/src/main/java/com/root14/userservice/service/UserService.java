package com.root14.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.ImageDto;
import com.root14.userservice.dto.ProfileDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.feign.ImageFeignClient;
import com.root14.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageFeignClient imageFeignClient;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserService(UserRepository userRepository, ImageFeignClient imageFeignClient, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.imageFeignClient = imageFeignClient;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Object> delete(DeleteDto deleteDto) {
        Optional<User> user = userRepository.findByUsername(deleteDto.getUsername());

        if (user.isPresent()) {
            User _user = user.get();
            _user.setEnabled(false);
            userRepository.save(_user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ProfileDto> getProfileData(String userName) {
        return userRepository.findByUsername(userName)
                .map(this::convertToProfileDto)
                .map(profileDto -> ResponseEntity.ok().body(profileDto)).orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    public ResponseEntity<Object> updateProfile(ProfileDto profileDto, String authenticatedUserId, MultipartFile multipartFile) {
        return userRepository.findById(authenticatedUserId)
                .map(user -> updateUserEntity(user, profileDto, multipartFile))
                .map(updatedUser -> {
                    userRepository.save(updatedUser);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    private ProfileDto convertToProfileDto(User user) {
        return ProfileDto.builder()
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .location(user.getLocation())
                .link(user.getLink())
                .imageId(user.getImageId())
                .bio(user.getBio())
                .build();
    }

    private User updateUserEntity(User user, ProfileDto profileDto, MultipartFile multipartFile) {
        user.setFullName(profileDto.getFullName());
        user.setPhone(profileDto.getPhone());
        user.setLocation(profileDto.getLocation());
        user.setLink(profileDto.getLink());

        //todo save to media service -> save imageId to mongodb

        String resultImageId = imageFeignClient.uploadImage(multipartFile);
        //todo parse  resultImageId -> response {"uuid":"26689368-99d1-4f4e-b100-2ce552411f7b"}

        try {
            ImageDto imageDto = objectMapper.readValue(resultImageId, ImageDto.class);
            user.setImageId(imageDto.getUuid());
        } catch (JsonProcessingException exception) {
            //todo error handle
        }

        user.setBio(profileDto.getBio());
        return user;
    }
}
