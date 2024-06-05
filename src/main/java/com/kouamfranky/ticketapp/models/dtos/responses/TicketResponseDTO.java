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
import java.util.stream.Collectors;

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

    public static TicketResponseDTO buildFromEntity(Ticket entity){
        return Objects.isNull(entity) ? null : TicketResponseDTO.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .description(entity.getDescription())
                .statut(entity.getStatut())
                .build();
    }

    public static List<TicketResponseDTO> buildFromEntityList(List<Ticket> entityList){
        return Objects.isNull(entityList) || entityList.isEmpty() ? new ArrayList<>() :
                entityList.stream().map(TicketResponseDTO::buildFromEntity).collect(Collectors.toList());
    }

    public static Page<TicketResponseDTO> buildFromEntityPage(Page<Ticket> entityPage){
        return Objects.isNull(entityPage) || entityPage.isEmpty() ? Page.empty() :
                entityPage.map(TicketResponseDTO::buildFromEntity);
    }
}
