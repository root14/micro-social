package com.root14.userservice.service;

import com.root14.userservice.entity.User;
import com.root14.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InterestManager {

    private final UserRepository userRepository;

    private final int MAX_INTERESTS = 99;
    private List<String> interestList;

    @Autowired
    public InterestManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> addInterest(String interest, String authenticatedUserId) {
        Optional<User> user = userRepository.findById(authenticatedUserId);

        if (user.isPresent()) {
            try {
                User u = user.get();

                interestList = u.getInterests();

                if (interestList.size() <= MAX_INTERESTS) {
                    interestList.add(interest);
                } else {
                    interestList.removeFirst();
                    interestList.add(interest);
                }
                u.setInterests(interestList);
                userRepository.save(u);
                return ResponseEntity.ok().body("Success");
            } catch (Exception e) {
                //todo log system
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public ResponseEntity<Object> getRandomInterest(String authenticatedUserId) {
        Optional<User> user = userRepository.findById(authenticatedUserId);

        if (user.isPresent()) {
            interestList = user.get().getInterests();

            Random rand = new Random();
            int index = rand.nextInt(interestList.size());
            return ResponseEntity.ok().body(interestList.get(index));
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

}
