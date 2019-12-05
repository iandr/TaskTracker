package com.geekbrains.erth.tracker.services;

import com.geekbrains.erth.tracker.data.UserRepository;
import com.geekbrains.erth.tracker.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getById (Long id) {
        return userRepository.findById(id).get();
    }
}
