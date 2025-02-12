package com.sky.cloud.delegate;

import com.sky.cloud.api2.TasksApiDelegate;
import com.sky.cloud.dto2.Task;
import com.sky.cloud.repository.TaskRepository; // Assuming a repository for task data
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class TaskApiDelegateImpl implements TasksApiDelegate {

  @Autowired private TaskRepository taskRepository;

  @Override
  public ResponseEntity<List<Task>> createTasks(List<Task> tasks) {


    List<Task> createdTasks = taskRepository.saveAll(tasks);


    return ResponseEntity.ok(createdTasks);
  }

  @Override
  public ResponseEntity<List<Task>> getAllTasks() {


    List<Task> tasks = taskRepository.findAll();


    return ResponseEntity.ok(tasks);
  }

  @Override
  public ResponseEntity<List<Task>> getTasksByIds(Integer id, List<Integer> ids) {

    List<Task> tasks = taskRepository.findAllById(ids);


    return ResponseEntity.ok(tasks);
  }

  @Override
  public ResponseEntity<List<Task>> getTasksByNames(List<String> names) {


    List<Task> tasks = taskRepository.findByNameIn(names);


    return ResponseEntity.ok(tasks);
  }

  @Override
  public ResponseEntity<List<Task>> updateTasks(List<Task> tasks) {


    List<Task> updatedTasks = taskRepository.saveAll(tasks);


    return ResponseEntity.ok(updatedTasks);
  }
}