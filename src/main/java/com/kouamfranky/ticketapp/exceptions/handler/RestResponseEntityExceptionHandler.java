package com.kouamfranky.ticketapp.exceptions.handler;

import com.kouamfranky.ticketapp.exceptions.BadRequestException;
import com.kouamfranky.ticketapp.exceptions.DuplicateEntryEntityException;
import com.kouamfranky.ticketapp.exceptions.MethodArgumentInvalidException;
import com.kouamfranky.ticketapp.exceptions.RessourceNotFoundException;
import com.kouamfranky.ticketapp.models.dtos.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:00
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.exceptions.handler
 **/
@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    private final String BAD_REQUEST_STATUS_CODE = "400 Status Code : ";


    @ExceptionHandler(value = {RessourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<String>> resourceNotFoundException(RessourceNotFoundException ex, WebRequest request) {
        log.error("404 Status Code : " + ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), ex.getFieldName(), new Date()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> badRequestException(BadRequestException ex, WebRequest request) {
        log.error(BAD_REQUEST_STATUS_CODE + ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), request.getDescription(false), new Date()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {MethodArgumentInvalidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Map<String, String>>> methodArgumentNoValidException(MethodArgumentInvalidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(err-> {
                    log.info(err.getField() + " " + err.getDefaultMessage());
                    errors.put(err.getField(), err.getDefaultMessage());
                }
        );
        log.error(BAD_REQUEST_STATUS_CODE + ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), errors, new Date()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DuplicateEntryEntityException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse<String>> duplicateEntryException(DuplicateEntryEntityException ex, WebRequest request) {
        log.error("409 Status Code : "+ ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), ex.getFieldName(), new Date()),
                HttpStatus.CONFLICT
        );
    }
}
