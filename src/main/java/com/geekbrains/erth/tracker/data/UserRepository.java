package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
