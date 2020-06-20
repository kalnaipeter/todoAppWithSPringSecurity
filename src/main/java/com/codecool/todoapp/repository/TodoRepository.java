package com.codecool.todoapp.repository;

import com.codecool.todoapp.model.Status;
import com.codecool.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t ORDER BY t.id")
    List<Todo> findAndOrderById();

    List<Todo> findAllByStatusOrderById(Status status);

    @Modifying
    @Transactional
    void deleteAllByStatus(Status status);

    @Modifying
    @Transactional
    @Query("UPDATE Todo SET status=:status")
    void toggleAll(@Param("status") String status);

    @Modifying
    @Transactional
    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Todo SET title=:title WHERE id=:id")
    void updateTitle(@Param("id") Long id, @Param("title") String title);

    Todo findTodoById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Todo SET status=:status WHERE id=:id")
    void toggleStatusById(@Param("id") Long id, @Param("status") Status status);
}

