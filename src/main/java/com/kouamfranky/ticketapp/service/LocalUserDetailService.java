package com.kouamfranky.ticketapp.service;

import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 06/juin/2024 -- 06:02
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service
 **/
@Component
public class LocalUserDetailService implements UserDetailsService {
    Logger LOGGER = LogManager.getLogger(LocalUserDetailService.class);
    private final UserRepository userRepository;

    public LocalUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            LOGGER.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else if (Boolean.FALSE.equals(user.getActive())) {
            LOGGER.error("Failed to authenticate since user account is inactive");
            throw new UsernameNotFoundException("User is inactive");
        }

        return new User(user);
    }
}
