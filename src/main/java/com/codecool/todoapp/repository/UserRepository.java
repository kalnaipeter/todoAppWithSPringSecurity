package com.codecool.todoapp.repository;

import com.codecool.todoapp.model.TodoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TodoAppUser, Long> {

    Optional<Object> findByUsername(String username);

}
