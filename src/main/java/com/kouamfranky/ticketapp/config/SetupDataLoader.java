package com.kouamfranky.ticketapp.config;

import com.kouamfranky.ticketapp.models.dtos.requests.UserRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserResponseDTO;
import com.kouamfranky.ticketapp.models.enumerations.RoleEnum;
import com.kouamfranky.ticketapp.repository.UserRepository;
import com.kouamfranky.ticketapp.service.inter.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 06/juin/2024 -- 05:49
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.config
 **/
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final UserService userService;
    private final UserRepository userRepository;

    public SetupDataLoader(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // Create initial user
        if (!userRepository.existsByEmail("kouamfranky@gmail.com"))
            createUserIfNotFound( RoleEnum.ROLE_ADMIN.name());
        alreadySetup = true;
    }
    @Transactional
    public UserResponseDTO createUserIfNotFound(String roles) {
        return userService.addUser(UserRequestDTO.builder()
                .email("kouamfranky@gmail.com")
                .username("franko")
                .password("Franko#10")
                .nom("Franko")
                .roles(roles)
                .build());
    }
}
