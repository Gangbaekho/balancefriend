package com.example.balancefriend.domain.todo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    @Query(value = "SELECT t FROM Todo t WHERE t.user.id=:userId ORDER BY t.id DESC")
    List<Todo> findByUserIdDesc(Long userId);

    @Query(value = "SELECT t FROM Todo t WHERE t.user.id=:userId ORDER BY t.id DESC")
    List<Todo> findByUserIdWithOffsetAndLimit(Long userId, Pageable pageable);

    @Query(value = "SELECT t FROM Todo t WHERE t.user.id=:userId ORDER BY t.id DESC")
    List<Todo> findByUserIdWithOffset(Long userId, Pageable pageable);

    @Query(value = "SELECT t FROM Todo t WHERE t.user.id=:userId ORDER BY t.id DESC")
    List<Todo> findByUserIdWithLimit(Long userId, Pageable pageable);


}
