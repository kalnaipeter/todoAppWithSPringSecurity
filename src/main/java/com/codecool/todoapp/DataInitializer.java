package com.codecool.todoapp;

import com.codecool.todoapp.model.Status;
import com.codecool.todoapp.model.Todo;
import com.codecool.todoapp.model.TodoAppUser;
import com.codecool.todoapp.repository.TodoRepository;
import com.codecool.todoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TodoRepository todos;

    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(TodoRepository todos, UserRepository users) {
        this.todos = todos;
        this.users = users;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        log.debug("initializing sample data...");
        Arrays.asList("todo1", "todo2", "todo3").forEach(v -> todos.saveAndFlush(Todo.builder().status(Status.ACTIVE)
                .title(v).build()));

        log.debug("printing all todos...");
        todos.findAll().forEach(v -> log.debug(" Todos :" + v.toString()));

        users.save(TodoAppUser.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList("ROLE_USER"))
            .build()
        );

        users.save(TodoAppUser.builder()
            .username("admin")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
            .build()
        );
        log.debug("printing all users...");
        users.findAll().forEach(v -> log.debug(" TodoAppUser :" + v.toString()));

        System.out.println(todos.findAll());
    }
}
