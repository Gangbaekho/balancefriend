package com.example.balancefriend.service;

import com.example.balancefriend.domain.todo.Todo;
import com.example.balancefriend.domain.todo.TodoRepository;
import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.*;
import com.example.balancefriend.exception.CTodoNotFoundException;
import com.example.balancefriend.exception.CUserAuthorizationException;
import com.example.balancefriend.exception.CUserNotFoundException;
import com.example.balancefriend.util.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {


    private static final Integer DEFAULT_SKIP = 0;
    private static final Integer DEFAULT_LIMIT = 100;

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public TodoCreateResponseDto createTodo(Long userId, TodoCreateRequestDto requestDto){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CUserNotFoundException("user not found id :" + userId));


        Todo todo;
        if(requestDto.isCompleted()){

            todo = Todo.builder()
                    .name(requestDto.getName())
                    .completed(requestDto.isCompleted())
                    .completedAt(LocalDateTime.now())
                    .build();
        }else{

            todo = Todo.builder()
                    .name(requestDto.getName())
                    .completed(requestDto.isCompleted())
                    .build();
        }

        todo.updateUser(user);

        todoRepository.save(todo);

        return new TodoCreateResponseDto(todo);
    }

    @Transactional
    public TodoUpdateResponseDto updateTodo(Long userId, TodoUpdateRequestDto requestDto){

        Todo todo = todoRepository.findById(requestDto.getTodoId())
                .orElseThrow(()-> new CTodoNotFoundException("todo not found id : " + requestDto.getTodoId()));

        if(!userId.equals(todo.getUser().getId())){
            throw new CUserAuthorizationException("user doesnt have authorization.");
        }

        todo.updateName(requestDto.getName());

        return new TodoUpdateResponseDto(todo);
    }

    @Transactional
    public TodoDeleteResponseDto deleteTodo(Long userId, Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new CTodoNotFoundException("todo not found id : " + todoId));

        if(!userId.equals(todo.getUser().getId())){
            throw new CUserAuthorizationException("user doesnt have authorization.");
        }

        todoRepository.delete(todo);

        return new TodoDeleteResponseDto(todo);
    }

    @Transactional(readOnly = true)
    public TodoGetFullResponseDto getSingleFullTodo(Long userId, Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new CTodoNotFoundException("todo not found id : " + todoId));

        if(!userId.equals(todo.getUser().getId())){
            throw new CUserAuthorizationException("user doesnt have authorization.");
        }

        return new TodoGetFullResponseDto(todo);
    }

    @Transactional(readOnly = true)
    public TodoGetPartialResponseDto getSinglePartialTodo(Long userId, Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new CTodoNotFoundException("todo not found id : " + todoId));

        if(!userId.equals(todo.getUser().getId())){
            throw new CUserAuthorizationException("user doesnt have authorization.");
        }

        return new TodoGetPartialResponseDto(todo);
    }

    @Transactional(readOnly = true)
    public List<TodoGetFullResponseDto> getListFullTodos(Long userId, Integer skip, Integer limit){

        if(Objects.isNull(skip)){
            skip = DEFAULT_SKIP;
        }

        if(Objects.isNull(limit)){
            limit = DEFAULT_LIMIT;
        }

        Pageable pageable = new OffsetBasedPageRequest(limit, skip);
        List<Todo> todos = todoRepository.findByUserIdWithOffsetAndLimit(userId, pageable);

        List<TodoGetFullResponseDto> responseDtos = todos.stream()
                        .map(todo -> new TodoGetFullResponseDto(todo))
                        .collect(Collectors.toList());

        return responseDtos;
    }

    @Transactional(readOnly = true)
    public List<TodoGetPartialResponseDto> getListPartialTodos(Long userId, Integer skip, Integer limit){

        if(Objects.isNull(skip)){
            skip = DEFAULT_SKIP;
        }

        if(Objects.isNull(limit)){
            limit = DEFAULT_LIMIT;
        }

        Pageable pageable = new OffsetBasedPageRequest(limit, skip);
        List<Todo> todos = todoRepository.findByUserIdWithOffsetAndLimit(userId, pageable);

        List<TodoGetPartialResponseDto> responseDtos = todos.stream()
                .map(todo -> new TodoGetPartialResponseDto(todo))
                .collect(Collectors.toList());

        return responseDtos;
    }

    @Transactional
    public TodoCompleteResponseDto completeTodo(Long userId, Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new CTodoNotFoundException("todo not found id : " + todoId));

        if(!userId.equals(todo.getUser().getId())){
            throw new CUserAuthorizationException("user doesnt have authorization.");
        }

        todo.updateComplete();
        todo.updateCompletedAt();

        return new TodoCompleteResponseDto(todo);
    }
}
