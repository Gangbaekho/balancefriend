package com.example.balancefriend.controller;

import com.example.balancefriend.dto.*;
import com.example.balancefriend.security.CurrentUser;
import com.example.balancefriend.security.UserPrincipal;
import com.example.balancefriend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{todoId}/full")
    public TodoGetFullResponseDto getSingleFullTodo(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "todoId") Long todoId){

        return todoService.getSingleFullTodo(userPrincipal.getId(), todoId);
    }

    @GetMapping("/list/full")
    public List<TodoGetFullResponseDto> getListFullTodos(@CurrentUser UserPrincipal userPrincipal,
                                                         @RequestParam(required = false) Integer skip,
                                                         @RequestParam(required = false) Integer limit){

        return todoService.getListFullTodos(userPrincipal.getId(),skip, limit);
    }

    @GetMapping("/{todoId}/partial")
    public TodoGetPartialResponseDto getSinglePartialTodo(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "todoId") Long todoId){

        return todoService.getSinglePartialTodo(userPrincipal.getId(), todoId);
    }

    @GetMapping("/list/partial")
    public List<TodoGetPartialResponseDto> getListPartialTodos(@CurrentUser UserPrincipal userPrincipal,
                                                               @RequestParam(required = false) Integer skip,
                                                               @RequestParam(required = false) Integer limit){

        return todoService.getListPartialTodos(userPrincipal.getId(), skip, limit);
    }

    @PostMapping("")
    public TodoCreateResponseDto createTodo(@CurrentUser UserPrincipal userPrincipal, @RequestBody TodoCreateRequestDto requestDto){

        return todoService.createTodo(userPrincipal.getId(),requestDto);
    }

    @PutMapping("")
    public TodoUpdateResponseDto updateTodo(@CurrentUser UserPrincipal userPrincipal, @RequestBody TodoUpdateRequestDto requestDto){

        return todoService.updateTodo(userPrincipal.getId(),requestDto);
    }

    @DeleteMapping("/{todoId}")
    public TodoDeleteResponseDto deleteTodo(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "todoId") Long todoId){

        return todoService.deleteTodo(userPrincipal.getId(),todoId);
    }

    @PostMapping("/{todoId}/complete")
    public TodoCompleteResponseDto completeTodo(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "todoId") Long todoId){

        return todoService.completeTodo(userPrincipal.getId(),todoId);
    }

}
