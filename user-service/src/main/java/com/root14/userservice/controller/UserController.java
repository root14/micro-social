package com.root14.userservice.controller;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.ProfileDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.exception.UserException;
import com.root14.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @PatchMapping("/updateProfile")
    public ResponseEntity<Object> register(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestBody ProfileDto profileDto) throws UserException {
        if (authenticatedUserId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.updateProfile(profileDto, authenticatedUserId));
    }

    @PostMapping("/updateInterest")
    public ResponseEntity<Object> updateInterests(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestParam String interest) {

        if (authenticatedUserId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.updateInterest(authenticatedUserId, interest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody DeleteDto deleteDto) {
        return userService.delete(deleteDto);
    }
}
