package com.kouamfranky.ticketapp.models.dtos.responses;

import com.kouamfranky.ticketapp.models.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 18:25
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.dtos.responses
 **/
@Builder
@Getter
@Setter
public class UserInfosDTO {
    private Long idUser;
    private String username;
    private String token;
    private String nom;

    public static UserInfosDTO buildUserInfosByUser(User user, String token){
        return UserInfosDTO.builder()
                .idUser(user.getId())
                .username(user.getUsername())
                .nom(user.getNom())
                .token(token)
                .build();
    }
}
