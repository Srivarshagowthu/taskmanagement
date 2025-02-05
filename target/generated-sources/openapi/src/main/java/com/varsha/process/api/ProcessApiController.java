package com.varsha.process.api;

import com.varsha.process.dto.Process;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-05T17:16:59.398219+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("${openapi.process.base-path:}")
public class ProcessApiController implements ProcessApi {

    private final ProcessApiDelegate delegate;

    public ProcessApiController(@Autowired(required = false) ProcessApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ProcessApiDelegate() {});
    }

    @Override
    public ProcessApiDelegate getDelegate() {
        return delegate;
    }

}
