package com.sky.cloud.repository;

import com.sky.cloud.dto2.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByNameIn(List<String> names);
}
