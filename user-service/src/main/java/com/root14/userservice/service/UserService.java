package com.root14.userservice.service;

import com.root14.userservice.dto.DeleteDto;
import com.root14.userservice.dto.ProfileDto;
import com.root14.userservice.entity.User;
import com.root14.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    //todo refactor it to weigh system
    public ResponseEntity<Boolean> updateInterest(String authenticatedUserId, String interest) {
        Optional<User> user = userRepository.findById(authenticatedUserId);

        if (user.isPresent()) {
            User _user = user.get();
            List<String> interestList = _user.getInterests();

            if (interestList == null) {
                interestList = new ArrayList<>();
            }

            interestList.add(interest);
            _user.setInterests(interestList);
            userRepository.save(_user);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * random one interest with tricky way.
     *
     * @return
     */
    private int randomInterest(int interestListCount) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(interestListCount);
    }

    public ResponseEntity<List<String>> getInterests() {
        //todo write after weigh system

        return ResponseEntity.ok(Arrays.asList());

    }

    public ResponseEntity<ProfileDto> getProfileData(String userName) {
        return userRepository.findByUsername(userName).map(this::convertToProfileDto).map(profileDto -> ResponseEntity.ok().body(profileDto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> updateProfile(ProfileDto profileDto, String authenticatedUserId) {
        return userRepository.findById(authenticatedUserId).map(user -> updateUserEntity(user, profileDto)).map(updatedUser -> {
            userRepository.save(updatedUser);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ProfileDto convertToProfileDto(User user) {
        return ProfileDto.builder().fullName(user.getFullName()).phone(user.getPhone()).location(user.getLocation()).link(user.getLink()).image(user.getImage()).bio(user.getBio()).build();
    }

    private User updateUserEntity(User user, ProfileDto profileDto) {
        user.setFullName(profileDto.getFullName());
        user.setPhone(profileDto.getPhone());
        user.setLocation(profileDto.getLocation());
        user.setLink(profileDto.getLink());
        user.setImage(profileDto.getImage());
        user.setBio(profileDto.getBio());
        return user;
    }
}
