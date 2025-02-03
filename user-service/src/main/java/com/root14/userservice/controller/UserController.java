package com.root14.userservice.controller;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.ProfileDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.exception.UserException;
import com.root14.userservice.service.InterestManager;
import com.root14.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final InterestManager interestManager;

    @Autowired
    public UserController(UserService userService, InterestManager interestManager) {
        this.userService = userService;
        this.interestManager = interestManager;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/getProfile")
    public ResponseEntity<Object> getProfileData(@RequestHeader("authenticated-user-id") @RequestParam String userName) {
        if (userName == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.getProfileData(userName));
    }

    //patch mapping not working. probably default client not support patch mapping. (feign client)
    @PostMapping(path = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateProfile(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestPart(value = "dto", required = false) ProfileDto profileDto, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws UserException {
        if (authenticatedUserId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.updateProfile(profileDto, authenticatedUserId, multipartFile));
    }

    @PostMapping("/updateInterest")
    public ResponseEntity<Object> updateInterests(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestParam String interest) {

        if (authenticatedUserId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(interestManager.addInterest(interest, authenticatedUserId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody DeleteDto deleteDto) {
        return userService.delete(deleteDto);
    }
}
