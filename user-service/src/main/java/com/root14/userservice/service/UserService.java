package com.root14.userservice.service;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.RegisterDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.exception.UserException;
import com.root14.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //todo add custom email password validation
    public ResponseEntity<Object> register(RegisterDto registerDto) throws UserException {
        try {
            User user = User.builder().email(registerDto.getEmail()).username(registerDto.getUsername()).password(registerDto.getPassword()).build();

            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //return ResponseEntity.badRequest().body("user cannot be created");

            throw UserException.builder().exception(e).errorMessage("User already exists!").errorCode(101).build();
        }
    }

    public ResponseEntity<Object> delete(DeleteDto deleteDto) {
        User user = userRepository.getUsersByUsername(deleteDto.getUsername());

        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
