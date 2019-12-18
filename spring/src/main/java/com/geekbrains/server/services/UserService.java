package com.geekbrains.server.services;

import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.User;
import com.geekbrains.server.repositories.UserRepository;
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

        public List<UserDto> findAllDtos() {
        return userRepository.findAllDtos();
    }
}
