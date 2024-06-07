package com.kouamfranky.ticketapp.models.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 18:35
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.dtos.requests
 **/
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "le nom d'utilisateur est obligatoire")
    private String username;
    @NotBlank(message = "le mot de passe est obligatoire")
    @Size(min = 6, message = "le mot de passe doit avoir au moins 6 caracteres")
    private String password;
}
