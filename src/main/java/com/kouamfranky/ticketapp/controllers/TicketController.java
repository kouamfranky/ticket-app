package com.kouamfranky.ticketapp.controllers;

import com.kouamfranky.ticketapp.models.dtos.ApiResponse;
import com.kouamfranky.ticketapp.models.dtos.requests.TicketRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.service.inter.TicketService;
import com.kouamfranky.ticketapp.service.inter.UserService;
import com.kouamfranky.ticketapp.utils.SecurityUtils;
import com.kouamfranky.ticketapp.utils.StringsUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * When: @created 06/juin/2024 -- 08:06
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.controllers
 **/
@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractController {

    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Operation(summary = "Récupérer tous les tickets de l'utilisateur connecter", description = "Retourne la liste des tickets paginées de l'utilisateur connecte à 20 elements par page par defaut, avec possibilite de faire une recherche dynamique via un token")
    @GetMapping(path = "/userconnected")
    public ResponseEntity<ApiResponse<Page<TicketResponseDTO>>> getAllTicketsUserConnected(
            @RequestParam(name = "token", defaultValue = "") @Parameter(description = "token de recherche dynamique sur le titre, la description et le statut du ticket") String token,
            @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "la page a recuperer") int page,
            @RequestParam(name="size", defaultValue = "20") @Parameter(description = "la nombre d'element a recuperer par page") int size ) {


        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                userService.findTicketsUsers(SecurityUtils.geUserConnected().getId(), token, PageRequest.of(page, size)), new Date()));
    }
    @Operation(summary = "Récupérer tous les tickets", description = "Retourne la liste des tickets paginées à 20 elements par page par defaut, avec possibilite de faire une recherche dynamique via un token")
    @GetMapping(path = "")
    public ResponseEntity<ApiResponse<Page<TicketResponseDTO>>> getAllTickets(
            @RequestParam(name = "token", defaultValue = "") @Parameter(description = "token de recherche dynamique sur le titre, la description et le statut du ticket") String token,
            @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "la page a recuperer") int page,
            @RequestParam(name="size", defaultValue = "20") @Parameter(description = "la nombre d'element a recuperer par page") int size ) {
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                ticketService.getAllTickets(token, PageRequest.of(page, size)), new Date()));
    }
    @Operation(summary = "Récupérer un ticket par son ID", description = "Retourne un ticket par son ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> getTicketById(@PathVariable("id") @Parameter(description = "ID du ticket") @NotNull(message = "l'identifiant du ticket est obligatoire") long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                ticketService.getTicketById(id), new Date()));
    }
    @Operation(summary = "Créer un ticket", description = "Permet de creer d'ajouter un ticket dans le systeme")
    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> addTicket(@Valid @RequestBody TicketRequestDTO dto, BindingResult result) {
        checkValidationFieldsOnDto(result);
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                ticketService.addTicket(dto), new Date()));
    }
    @Operation(summary = "Modifier un ticket", description = "Permet de mettre a jour un ticket dans le systeme")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> updateTicket(@Valid @RequestBody TicketRequestDTO dto,
                                                                       @PathVariable("id") @Parameter(description = "ID du ticket a modifier") long id, BindingResult result) {
        checkValidationFieldsOnDto(result);
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                ticketService.updateTicket(dto, id), new Date()));
    }
    @Operation(summary = "Assigner un ticket à un utilisateur", description = "Permet de mettre a jour un ticket dans le systeme")
    @PutMapping(path = "/{id}/assign/{useId}")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> affectTicketToUser( @PathVariable("id") @Parameter(description = "ID du ticket a modifier") long idTicket,
                                                                              @PathVariable("useId") @Parameter(description = "ID de l'utilisateur, 0 si le ticket ne doit etre affecter a personne") long useId) {

        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                ticketService.affectTicketToUser(idTicket, useId), new Date()));
    }



    @Operation(summary = "Supprimer un ticket par son ID", description = "Permet de Supprimer un ticket dans le systeme")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTicket(@PathVariable("id") @Parameter(description = "ID du ticket a supprimer") long id) {

        ticketService.deleteTicket(id);
        return ResponseEntity.ok(new ApiResponse<>(true, StringsUtils.SUCCESS_MESSAGE,
                "OK", new Date()));
    }
}
