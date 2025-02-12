package com.example.demo;
import com.example.demo.Exceptions.CyborgException;
import com.example.demo.dto.ModelApiResponse;
import com.example.demo.dto.Process;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public interface ProcessService {

  /**
   * Create new processes
   *
   * @param process The list of processes to be created
   * @return ResponseEntity with the status and response message
   */
  ModelApiResponse createProcesses(List<Process> process) throws CyborgException;

    /**
     * Fetch all processes with pagination
     * @param page The page number for pagination
     * @param size The number of items per page
     * @return ResponseEntity with a list of processes
     */
    Page<Process> getAllProcesses(Integer page, Integer size) throws CyborgException;

    /**
     * Get a process by ID
     * @param id The ID of the process to be retrieved
     * @return ResponseEntity with the found process or a 404 status if not found
     */
    ResponseEntity<Process> getProcessById(Integer id) throws CyborgException;

    /**
     * Get a process by name
     * @param name The name of the process to be retrieved
     * @return ResponseEntity with the found process or a 404 status if not found
     */
    ResponseEntity<Process> getProcessByName(String name);

    /**
     * Soft delete a process by ID
     * @param id The ID of the process to be soft deleted
     * @return ResponseEntity with the status and message
     */
    ResponseEntity<ModelApiResponse> softDelete(Integer id) throws CyborgException;

    /**
     * Batch update processes
     * @param process The list of processes to be updated
     * @return ResponseEntity with the updated processes or a 204 status if no processes were updated
     */
    ResponseEntity<List<Process>> updateProcesses(List<Process> process) throws CyborgException;
}
