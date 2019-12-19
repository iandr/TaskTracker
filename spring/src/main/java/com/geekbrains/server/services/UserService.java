package com.geekbrains.server.services;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.Role;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.entities.User;
import com.geekbrains.server.repositories.RoleRepository;
import com.geekbrains.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> findAllDtos() {
        return userRepository.findAllDtos();
    }
}