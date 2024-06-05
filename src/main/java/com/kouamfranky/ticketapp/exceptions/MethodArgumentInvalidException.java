package com.kouamfranky.ticketapp.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 03:58
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.exceptions
 **/
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class MethodArgumentInvalidException extends RuntimeException {
    private final List<FieldError> fieldErrors;

    public MethodArgumentInvalidException(String message) {
        super(message);
        this.fieldErrors = null;
    }

    public MethodArgumentInvalidException(String message, List<FieldError> cause) {
        super(message);
        this.fieldErrors = cause;
    }
}
