package com.kouamfranky.ticketapp.models.dtos.responses;

import com.kouamfranky.ticketapp.models.entities.Ticket;
import com.kouamfranky.ticketapp.models.enumerations.StatutTicketEnum;
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
public class TicketResponseDTO {
    private Long id;
    private String titre;
    private String description;
    private StatutTicketEnum statut;
    private Long idUser;

    /**
     * @param entity l'entitée Ticket
     * @return la DTO de reponse d'un ticket
     */
    public static TicketResponseDTO buildFromEntity(Ticket entity){
        if (Objects.isNull(entity) ) return null;
        return TicketResponseDTO.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .description(entity.getDescription())
                .statut(entity.getStatut())
                .idUser(Objects.nonNull(entity.getUser()) ? entity.getUser().getId() : null)
                .build();
    }

    /**
     * @param entityList liste non paginée de ticket
     * @return liste non paginée de ticket
     */
    public static List<TicketResponseDTO> buildFromEntityList(List<Ticket> entityList){
        return Objects.isNull(entityList) || entityList.isEmpty() ? new ArrayList<>() :
                entityList.stream().map(TicketResponseDTO::buildFromEntity).toList();
    }
    /**
     * @param entityPage liste Paginée de ticket
     * @return liste paginée de ticket
     */
    public static Page<TicketResponseDTO> buildFromEntityPage(Page<Ticket> entityPage){
        return Objects.isNull(entityPage) || entityPage.isEmpty() ? Page.empty() :
                entityPage.map(TicketResponseDTO::buildFromEntity);
    }
}
