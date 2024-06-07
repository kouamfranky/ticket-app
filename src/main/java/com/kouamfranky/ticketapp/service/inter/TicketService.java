package com.kouamfranky.ticketapp.service.inter;

import com.kouamfranky.ticketapp.models.dtos.requests.TicketRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 06/juin/2024 -- 08:08
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service.inter
 **/
public interface TicketService {
    Page<TicketResponseDTO> getAllTickets(String token, Pageable pageable);

    TicketResponseDTO getTicketById(long id);

    TicketResponseDTO addTicket(TicketRequestDTO dto);

    TicketResponseDTO updateTicket(TicketRequestDTO dto, long id);

    TicketResponseDTO affectTicketToUser(long idTicket, long useId);

    void deleteTicket(long id);
}
