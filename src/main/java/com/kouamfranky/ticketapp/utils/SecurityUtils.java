package com.kouamfranky.ticketapp.utils;

import com.kouamfranky.ticketapp.models.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 07/juin/2024 -- 05:44
 *
 * @author name :  Franky Brice on 07/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.utils
 **/
public class SecurityUtils {
    private SecurityUtils(){}
    public static User geUserConnected(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
