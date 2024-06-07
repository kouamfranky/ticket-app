package com.kouamfranky.ticketapp.service.impl;

import com.kouamfranky.ticketapp.exceptions.RessourceNotFoundException;
import com.kouamfranky.ticketapp.models.dtos.requests.TicketRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.entities.Ticket;
import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.repository.TicketRepository;
import com.kouamfranky.ticketapp.repository.UserRepository;
import com.kouamfranky.ticketapp.service.inter.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.kouamfranky.ticketapp.utils.StringsUtils.ID;
import static com.kouamfranky.ticketapp.utils.StringsUtils.TICKET;

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
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<TicketResponseDTO> getAllTickets(String token, Pageable pageable) {
        return TicketResponseDTO.buildFromEntityPage(ticketRepository.findAllTicketByToken("%" + token + "%", pageable));
    }

    @Override
    public TicketResponseDTO getTicketById(long id) {
        return TicketResponseDTO.buildFromEntity(getTicket(id));
    }
    public Ticket getTicket(Long idTicket) {
        return ticketRepository.findById(idTicket).orElseThrow(
                ()-> new RessourceNotFoundException(TICKET, ID, idTicket));
    }
    @Override
    public TicketResponseDTO addTicket(TicketRequestDTO dto) {
        return TicketResponseDTO.buildFromEntity(ticketRepository.save(TicketRequestDTO.buildToCreateFromDTO(dto)));
    }

    @Override
    public TicketResponseDTO updateTicket(TicketRequestDTO dto, long id) {
        Ticket ticket = getTicket(id);
        Ticket ticketToUpdate = TicketRequestDTO.buildToUpdateFromDTO(ticket, dto);
        return TicketResponseDTO.buildFromEntity(ticketRepository.save(ticketToUpdate));
    }

    @Override
    public TicketResponseDTO affectTicketToUser(long idTicket, long useId) {
        Ticket ticket = getTicket(idTicket);
        User user = userRepository.findById(useId).orElse(null);
        ticket.setUser(user);
        return TicketResponseDTO.buildFromEntity(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(long id){
        ticketRepository.deleteById(getTicket(id).getId());
    }
}
