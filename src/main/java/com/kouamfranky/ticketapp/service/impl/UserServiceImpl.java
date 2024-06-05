package com.kouamfranky.ticketapp.service.impl;

import com.kouamfranky.ticketapp.exceptions.RessourceNotFoundException;
import com.kouamfranky.ticketapp.models.dtos.requests.UserRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserResponseDTO;
import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.repository.TicketRepository;
import com.kouamfranky.ticketapp.repository.UserRepository;
import com.kouamfranky.ticketapp.service.inter.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kouamfranky.ticketapp.utils.StringsUtils.ID;
import static com.kouamfranky.ticketapp.utils.StringsUtils.USER;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:55
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service.impl
 **/
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, TicketRepository ticketRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(String token, Pageable pageable) {
        return UserResponseDTO.buildFromEntityPage(userRepository.findAllUserByToken("%" +token + "%", pageable));
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO dto) {
        User user = UserRequestDTO.buildToCreateFromDTO(dto, passwordEncoder.encode(dto.getPassword()));
        return UserResponseDTO.buildFromEntity(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO dto, Long idUser) {
        User user = UserRequestDTO.buildToUpdateFromDTO(getUser(idUser), dto);
        return UserResponseDTO.buildFromEntity(userRepository.save(user));
    }

    private User getUser(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(
                ()-> new RessourceNotFoundException(USER, ID, idUser));
    }

    @Override
    public Page<TicketResponseDTO> findTicketsUsers(Long idUser, String token, Pageable pageable) {
        return TicketResponseDTO.buildFromEntityPage(ticketRepository.findByUserAndToken(getUser(idUser), "%" +token + "%", pageable));
    }
}
