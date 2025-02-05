package com.varsha.process.api;

import com.varsha.process.dto.Process;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link ProcessApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-05T17:16:59.398219+05:30[Asia/Kolkata]")
public interface ProcessApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /process : Create a new process
     *
     * @param process Process to be created (optional)
     * @return Process created successfully (status code 201)
     *         or Invalid input data (status code 400)
     * @see ProcessApi#createProcess
     */
    default ResponseEntity<Void> createProcess(Process process) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /process/{id} : Delete a process by ID
     *
     * @param id  (required)
     * @return Process deleted successfully (status code 204)
     *         or Process not found (status code 404)
     * @see ProcessApi#deleteProcess
     */
    default ResponseEntity<Void> deleteProcess(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /process/{id} : Get a process by ID
     *
     * @param id  (required)
     * @return Process retrieved successfully (status code 200)
     *         or Process not found (status code 404)
     * @see ProcessApi#getProcess
     */
    default ResponseEntity<Process> getProcess(Integer id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"Sample Process\", \"description\" : \"This is a sample process description.\", \"id\" : 1, \"title\" : \"Sample Process Title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /process/{id} : Update a process by ID
     *
     * @param id  (required)
     * @param process Process data to be updated (optional)
     * @return Process updated successfully (status code 200)
     *         or Invalid input data (status code 400)
     *         or Process not found (status code 404)
     * @see ProcessApi#updateProcess
     */
    default ResponseEntity<Void> updateProcess(Integer id,
        Process process) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
