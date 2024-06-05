package com.kouamfranky.ticketapp.models.dtos.requests;

import com.kouamfranky.ticketapp.models.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:57
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.dtos.requests
 **/
@Builder
@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "l'adresse email de l'utilisateur est obligatoire")
    @NotNull(message = "l'adresse email de l'utilisateur est obligatoire")
    @Schema(description = "email de l'utilisateur", example = "kouamfranky@gmail.com")
    private String email;
    @NotBlank(message = "le username de l'utilisateur est obligatoire")
    @NotNull(message = "le username de l'utilisateur est obligatoire")
    @Schema(description = "username de l'utilisateur", example = "kouamfranky")
    private String username;
    @NotBlank(message = "le mot de passe de l'utilisateur est obligatoire")
    @NotNull(message = "le mot de passe de l'utilisateur est obligatoire")
    @Schema(description = "mot de passe de l'utilisateur", example = "PassWord123*")
    private String password;
    @Schema(description = "numéro de téléphone de l'utilisateur", example = "698720459")
    private String telephone;
    @Schema(description = "adresse de l'utilisateur", example = "Yaounde, Nkolbisong")
    private String adresse;
    @NotBlank(message = "le nom de l'utilisateur est obligatoire")
    @NotNull(message = "le nom de l'utilisateur est obligatoire")
    @Schema(description = "nom de l'utilisateur", example = "kouam")
    private String nom;
    @Schema(description = "prenom de l'utilisateur", example = "franky")
    private String prenom;

    public static User buildToCreateFromDTO(UserRequestDTO dto, String password){
        return User.UserBuilder.anUser()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(password)
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .build();
    }

    public static User buildToUpdateFromDTO(User user, UserRequestDTO dto){
        user.setEmail(dto.getEmail());
        user.setTelephone(dto.getTelephone());
        user.setAdresse(dto.getAdresse());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        return user;
    }
}
