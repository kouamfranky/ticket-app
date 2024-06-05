package com.kouamfranky.ticketapp.controllers;

import com.kouamfranky.ticketapp.exceptions.MethodArgumentInvalidException;
import com.kouamfranky.ticketapp.models.dtos.ApiResponse;
import com.kouamfranky.ticketapp.models.dtos.requests.LoginRequest;
import com.kouamfranky.ticketapp.models.dtos.responses.UserInfosDTO;
import com.kouamfranky.ticketapp.service.inter.UserService;
import com.kouamfranky.ticketapp.utils.StringsUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 18:34
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.controllers
 **/
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserInfosDTO>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentInvalidException(StringsUtils.BINDING_RESULT_ERROR, result.getFieldErrors());
        }
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.processUserLogin(loginRequest), new Date()));
    }
}
