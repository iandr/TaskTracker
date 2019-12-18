package com.geekbrains.server.repositories;

import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT new com.geekbrains.gwt.common.UserDto(t.id, t.name) FROM User t")
    List<UserDto> findAllDtos();
}
