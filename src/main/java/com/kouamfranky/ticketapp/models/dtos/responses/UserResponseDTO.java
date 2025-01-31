package com.kouamfranky.ticketapp.models.dtos.responses;

import com.kouamfranky.ticketapp.models.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:58
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.dtos.responses
 **/
@Builder
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String telephone;
    private String adresse;
    private String nom;
    private String prenom;

    /**
     * @param entity l'entitée User
     * @return la DTO de reponse d'un utilisateur
     */
    public static UserResponseDTO buildFromEntity(User entity){
        return Objects.isNull(entity) ? null : UserResponseDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .telephone(entity.getTelephone())
                .adresse(entity.getAdresse())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .build();
    }


    /**
     * @param entityList liste non paginée d'utilisateur
     * @return liste non paginée d'utilisateur
     */
    public static List<UserResponseDTO> buildFromEntityList(List<User> entityList){
        return Objects.isNull(entityList) || entityList.isEmpty() ? new ArrayList<>() :
                entityList.stream().map(UserResponseDTO::buildFromEntity).toList();
    }
    /**
     * @param entityPage liste Paginée d'utilisateur
     * @return liste paginée d'utilisateur
     */
    public static Page<UserResponseDTO> buildFromEntityPage(Page<User> entityPage){
        return Objects.isNull(entityPage) || entityPage.isEmpty() ? Page.empty() :
                entityPage.map(UserResponseDTO::buildFromEntity);
    }
}
