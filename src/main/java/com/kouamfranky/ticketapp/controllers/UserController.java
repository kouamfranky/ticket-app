package com.kouamfranky.ticketapp.controllers;

import com.kouamfranky.ticketapp.exceptions.MethodArgumentInvalidException;
import com.kouamfranky.ticketapp.models.dtos.ApiResponse;
import com.kouamfranky.ticketapp.models.dtos.requests.UserRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserResponseDTO;
import com.kouamfranky.ticketapp.service.inter.UserService;
import com.kouamfranky.ticketapp.utils.StringsUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:52
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.web
 **/
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Récupérer tous les utilisateurs", description = "Retourne la liste des utilisateurs paginer à 20 elements par page par defaut, avec possibilite de faire une recherche dynamique via un token")
    @GetMapping(path = "/users")
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getAllUsers(@RequestParam(name = "token") @Parameter(description = "token de recherche dynamique sur le nom, prenom, email et username des utilisateurs") String token,
                                                                          @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "la page a recuperer") int page,
                                                                          @RequestParam(name="size", defaultValue = "20") @Parameter(description = "la nombre d'element a recuperer par page") int size ) {
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.getAllUsers(token, PageRequest.of(page, size)), new Date()));
    }
    @Operation(summary = "Récupérer les tickets assignés à l'utilisateur", description = "Retourne la liste des tickets assignés a un utilisateur paginer à 20 elements par page par defaut, avec possibilite de faire une recherche dynamique via un token")
    @GetMapping(path = "/users/{id}/ticket")
    public ResponseEntity<ApiResponse<Page<TicketResponseDTO>>> findTicketsUsers(@PathVariable("id") @Parameter(description = "ID de l'utilisateur a modifier") Long idUser,
                                                                                 @RequestParam(name = "token") @Parameter(description = "token de recherche dynamique sur le titre et la description des tickets") String token,
                                                                                 @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "la page a recuperer") int page,
                                                                                 @RequestParam(name="size", defaultValue = "20") @Parameter(description = "la nombre d'element a recuperer par page") int size) {
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.findTicketsUsers(idUser, token, PageRequest.of(page, size)), new Date()));
    }
    @Operation(summary = "Créer un utilisateur", description = "Permet de creer d'ajouter un utilisateur dans le systeme")
    @PostMapping(path = "/users")
    public ResponseEntity<ApiResponse<UserResponseDTO>> addUser(@Valid @RequestBody UserRequestDTO dto, BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentInvalidException(StringsUtils.BINDING_RESULT_ERROR, result.getFieldErrors());
        }
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.addUser(dto), new Date()));
    }
    @Operation(summary = "Modifier un utilisateur", description = "Permet de mettre a jour un utilisateur dans le systeme")
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@Valid @RequestBody UserRequestDTO dto,
                                                                   @PathVariable("id") @Parameter(description = "ID de l'utilisateur a modifier") Long idUser, BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentInvalidException(StringsUtils.BINDING_RESULT_ERROR, result.getFieldErrors());
        }
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.updateUser(dto, idUser), new Date()));
    }
}
