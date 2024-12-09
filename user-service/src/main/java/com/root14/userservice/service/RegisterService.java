package com.root14.userservice.service;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.RegisterDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    //todo add custom email password validation
    public boolean register(RegisterDto registerDto) {
        try {
            User user = User.builder().email(registerDto.getEmail()).username(registerDto.getUsername()).password(registerDto.getPassword()).build();

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            //todo add global exception handle
            return false;
        }
    }

    public boolean delete(DeleteDto deleteDto) {
        User user = userRepository.getUsersByUsername(deleteDto.getUsername());

        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


}
