package com.kouamfranky.ticketapp.models.dtos.requests;

import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.models.enumerations.RoleEnum;
import com.kouamfranky.ticketapp.validators.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

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
@Validated
public class UserRequestDTO {
    @ValidEmail
    @NotBlank(message = "l'adresse email de l'utilisateur est obligatoire")
    @NotNull(message = "l'adresse email de l'utilisateur est obligatoire")
    @Schema(description = "email de l'utilisateur", example = "kouamfranky@gmail.com")
    @Size(min = 6, message = "l'adresse email doit avoir au minimun 6 caracteres")
    private String email;
    @NotBlank(message = "le username de l'utilisateur est obligatoire")
    @NotNull(message = "le username de l'utilisateur est obligatoire")
    @Schema(description = "username de l'utilisateur", example = "kouamfranky")
    @Size(min = 4, message = "le username doit avoir au minimun 4 caracteres")
    @Size(max = 20, message = "le username doit avoir au maximun 20 caracteres")
    private String username;
    @NotBlank(message = "le mot de passe de l'utilisateur est obligatoire")
    @NotNull(message = "le mot de passe de l'utilisateur est obligatoire")
    @Schema(description = "mot de passe de l'utilisateur", example = "PassWord123*")
    private String password;
    @Schema(description = "numéro de téléphone de l'utilisateur", example = "698720459")
    private String telephone;
    @Schema(description = "adresse de l'utilisateur", example = "Yaounde, Nkolbisong")
    private String adresse;
    @Schema(description = "nom de l'utilisateur", example = "kouam")
    private String nom;
    @Schema(description = "prenom de l'utilisateur", example = "franky")
    private String prenom;
    private String roles;

    public static User buildToCreateFromDTO(UserRequestDTO dto, String password){
        return User.UserBuilder.anUser()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(password)
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .roles(Objects.nonNull(dto.getRoles()) ? dto.getRoles() : RoleEnum.ROLE_USER.name())
                .active(true)
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
