package com.kouamfranky.ticketapp.models.dtos.requests;

import com.kouamfranky.ticketapp.models.entities.Ticket;
import com.kouamfranky.ticketapp.models.enumerations.StatutTicketEnum;
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
 * When: @created 05/juin/2024 -- 04:58
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.dtos.requests
 **/
@Builder
@Getter
@Setter
public class TicketRequestDTO {

    @NotBlank(message = "le titre du ticket est obligatoire")
    @NotNull(message = "le titre du ticket est obligatoire")
    @Schema(description = "titre du ticket", example = "faire l'api de listing des utilisateurs")
    private String titre;

    @Schema(description = "description du ticket", example = "il est question d'implementer une api qui permettra de lister les utilisateurs du systeme")
    private String description;

    @Schema(description = "statut du ticket", example = "ENCOURS", allowableValues = "ENCOURS,TERMINER,ANNULER")
    private String statut;

    public static Ticket buildToCreateFromDTO(TicketRequestDTO dto){
        return Ticket.TicketBuilder.aTicket()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .statut(StatutTicketEnum.ENCOURS)
                .build();
    }

    public static Ticket buildToUpdateFromDTO(Ticket ticket, TicketRequestDTO dto){
        ticket.setTitre(dto.getTitre());
        ticket.setDescription(dto.getDescription());
        ticket.setStatut(StatutTicketEnum.valueOf(dto.getStatut()));
        return ticket;
    }
}
