package com.codecool.todoapp.controller;

import com.codecool.todoapp.model.Status;
import com.codecool.todoapp.model.Todo;
import com.codecool.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private static final String SUCCESS = "{\"success\":true}";

    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/addTodo")
    public String addTodo(@RequestParam("todo-title") String title) {
        Todo todo = Todo.builder()
                .title(title)
                .status(Status.ACTIVE)
                .build();
        todoRepository.saveAndFlush(todo);
        return SUCCESS;
    }

//    @PostMapping("/list")
//    public String listTodos(@RequestParam("status") String status) throws JSONException {
//        JSONArray todos = new JSONArray();
//        List<Todo> todoList;
//
//        if(status.isEmpty()) {
//            todoList = todoRepository.findAndOrderById();
//        } else {
//            todoList = todoRepository.findAllByStatusOrderById(Status.valueOf(status.toUpperCase()));
//        }
//
//        for (Todo todo : todoList) {
//            JSONObject jsonTodo = new JSONObject();
//            Status todoStatus = todo.getStatus();
//            jsonTodo.put("id", todo.getId());
//            jsonTodo.put("title", todo.getTitle());
//            jsonTodo.put("complete", todoStatus.equals(Status.COMPLETE));
//            todos.put(jsonTodo);
//        }
//        return todos.toString();
//    }

    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.POST)
    public List<Todo> listAllToDos(@RequestBody String status) {
        String requestStatusFieldValue = status.substring(7).toUpperCase();
        if (requestStatusFieldValue.equals("")) {
            return todoRepository.findAll();
        }
        return todoRepository.findAllByStatusOrderById(Status.valueOf(requestStatusFieldValue));
    }

    @DeleteMapping("/todos/completed")
    public String deleteAllCompleted(){
        todoRepository.deleteAllByStatus(Status.COMPLETE);
        return SUCCESS;
    }

    @PutMapping("/todos/toggle_all")
    public String toggleAll(@RequestParam("toggle-all")String complete){
        boolean ifComplete = complete.equals("true");
        if (ifComplete){
            todoRepository.toggleAll(String.valueOf(Status.COMPLETE));
        }else{
            todoRepository.toggleAll(String.valueOf(Status.ACTIVE));
        }
        return SUCCESS;
    }

    @DeleteMapping("/todos/{id}")
    public String deleteById(@PathVariable("id")Long id){
        todoRepository.deleteById(id);
        return SUCCESS;
    }

    @PutMapping("/todos/{id}")
    public String updateTitle(@PathVariable("id")Long id, @RequestParam("todo-title") String title){
        todoRepository.updateTitle(id,title);
        return SUCCESS;
    }

    @GetMapping("/todos/{id}")
    public String findById(@PathVariable("id")Long id){
        return todoRepository.findTodoById(id).getTitle();
    }

    @PutMapping("/todos/{id}/toggle_status")
    public String toggleStatusById(@PathVariable("id") Long id, @RequestParam("status") String status) {
        boolean completed = status.equals("true");
        if(completed) {
            todoRepository.toggleStatusById(id,Status.COMPLETE);
        } else {
            todoRepository.toggleStatusById(id,Status.ACTIVE);
        }
        return SUCCESS;
    }

}