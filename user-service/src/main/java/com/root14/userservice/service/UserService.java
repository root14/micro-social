package com.root14.userservice.service;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
