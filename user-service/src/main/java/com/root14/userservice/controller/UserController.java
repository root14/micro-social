package com.root14.userservice.controller;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.exception.UserException;
import com.root14.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/updateProfile")
    public ResponseEntity<Object> register() throws UserException {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody DeleteDto deleteDto) {
        return userService.delete(deleteDto);
    }

}
