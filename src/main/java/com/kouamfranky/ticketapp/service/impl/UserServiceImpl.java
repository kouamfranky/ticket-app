package com.kouamfranky.ticketapp.service.impl;

import com.kouamfranky.ticketapp.exceptions.BadRequestException;
import com.kouamfranky.ticketapp.exceptions.DuplicateEntryEntityException;
import com.kouamfranky.ticketapp.exceptions.RessourceNotFoundException;
import com.kouamfranky.ticketapp.models.dtos.requests.LoginRequest;
import com.kouamfranky.ticketapp.models.dtos.requests.UserRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserInfosDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserResponseDTO;
import com.kouamfranky.ticketapp.models.entities.User;
import com.kouamfranky.ticketapp.repository.TicketRepository;
import com.kouamfranky.ticketapp.repository.UserRepository;
import com.kouamfranky.ticketapp.securities.TokenProvider;
import com.kouamfranky.ticketapp.service.inter.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kouamfranky.ticketapp.utils.StringsUtils.*;

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
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserRepository userRepository, TicketRepository ticketRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(String token, Pageable pageable) {
        return UserResponseDTO.buildFromEntityPage(userRepository.findAllUserByToken("%" +token + "%", pageable));
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new DuplicateEntryEntityException(String.format(USERMANE_IS_ALREADY_EXIST, dto.getUsername()));
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new DuplicateEntryEntityException(String.format(EMAIL_IS_ALREADY_EXIST, dto.getEmail()));

        User user = UserRequestDTO.buildToCreateFromDTO(dto, passwordEncoder.encode(dto.getPassword()));
        return UserResponseDTO.buildFromEntity(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO dto, Long idUser) {
        User user = getUser(idUser);
        if (!user.getUsername().equals(dto.getUsername()) && userRepository.existsByUsername(dto.getUsername()))
            throw new DuplicateEntryEntityException(String.format(USERMANE_IS_ALREADY_EXIST, dto.getUsername()));
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail()))
            throw new DuplicateEntryEntityException(String.format(EMAIL_IS_ALREADY_EXIST, dto.getEmail()));

        User userToUpdate = UserRequestDTO.buildToUpdateFromDTO(user, dto);
        return UserResponseDTO.buildFromEntity(userRepository.save(userToUpdate));
    }


    private User getUser(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(
                ()-> new RessourceNotFoundException(USER, ID, idUser));
    }

    @Override
    public Page<TicketResponseDTO> findTicketsUsers(Long idUser, String token, Pageable pageable) {
        return TicketResponseDTO.buildFromEntityPage(ticketRepository.findByUserAndToken(getUser(idUser), "%" +token + "%", pageable));
    }

    @Override
    public UserInfosDTO processUserLogin(LoginRequest loginRequest) {
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            throw new RessourceNotFoundException("Utilisateur", "username", loginRequest.getUsername());
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String jwt = tokenProvider.createToken(authentication);

            return UserInfosDTO.buildUserInfosByUser(user,jwt);
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
