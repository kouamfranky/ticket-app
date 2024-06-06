package com.kouamfranky.ticketapp.service.impl;

import com.kouamfranky.ticketapp.models.dtos.requests.TicketRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.service.inter.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 06/juin/2024 -- 08:08
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service.impl
 **/
@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public Page<TicketResponseDTO> getAllTickets(String token, Pageable pageable) {
        return null;
    }

    @Override
    public TicketResponseDTO getTicketById(long id) {
        return null;
    }

    @Override
    public TicketResponseDTO addTicket(TicketRequestDTO dto) {
        return null;
    }

    @Override
    public TicketResponseDTO updateTicket(TicketRequestDTO dto, long id) {
        return null;
    }

    @Override
    public TicketResponseDTO affectTicketToUser(long idTicket, long useId) {
        return null;
    }
}
