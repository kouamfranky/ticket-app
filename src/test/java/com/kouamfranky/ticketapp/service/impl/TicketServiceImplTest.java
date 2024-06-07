package com.kouamfranky.ticketapp.service.impl;

import com.kouamfranky.ticketapp.exceptions.RessourceNotFoundException;
import com.kouamfranky.ticketapp.models.dtos.requests.TicketRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.entities.Ticket;
import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.models.enumerations.StatutTicketEnum;
import com.kouamfranky.ticketapp.repository.TicketRepository;
import com.kouamfranky.ticketapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 07/juin/2024 -- 05:56
 *
 * @author name :  Franky Brice on 07/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service.impl
 **/
@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;
    private TicketRequestDTO ticketRequestDTO;
    private TicketResponseDTO ticketResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitre("Test Ticket");

        ticketRequestDTO = TicketRequestDTO.builder()
                .titre("Test Ticket")
                .description("description ticket")
                .statut("ENCOURS")
                .build();

        ticketResponseDTO = TicketResponseDTO.builder()
                .id(1L)
                .titre("Test Ticket")
                .description("description ticket")
                .statut(StatutTicketEnum.ENCOURS)
                .build();

        user = User.UserBuilder.anUser()
                .id(1L)
                .username("usernameTest")
                .email("emailtest@gmail.com")
                .nom("Test User")
                .build();
    }

    @Test
    void testGetAllTickets() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Ticket> page = new PageImpl<>(Collections.singletonList(ticket));
        when(ticketRepository.findAllTicketByToken(anyString(), eq(pageable))).thenReturn(page);

        Page<TicketResponseDTO> result = ticketService.getAllTickets("token", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(ticketRepository, times(1)).findAllTicketByToken(anyString(), eq(pageable));
    }

    @Test
    void testGetTicketById() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        TicketResponseDTO result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void testAddTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketResponseDTO result = ticketService.addTicket(ticketRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void testUpdateTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        ticketRequestDTO.setTitre("titre update");

        TicketResponseDTO result = ticketService.updateTicket(ticketRequestDTO, 1L);

        assertNotNull(result);
        assertEquals(ticketResponseDTO.getId(), result.getId());
        assertEquals("titre update", result.getTitre());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
    @Test
    void testAffectTicketToUser() {
        // Given
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // When
        TicketResponseDTO result = ticketService.affectTicketToUser(ticket.getId(), user.getId());

        // Then
        assertNotNull(result);
        verify(ticketRepository, times(1)).findById(ticket.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(ticketRepository, times(1)).save(ticket);
    }
    @Test
    void testDeleteTicket() {
        // Given
        long id = 1L;
        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).deleteById(id);

        // When
        ticketService.deleteTicket(id);

        // Then
        verify(ticketRepository, times(1)).findById(id);
        verify(ticketRepository, times(1)).deleteById(id);
    }
}
