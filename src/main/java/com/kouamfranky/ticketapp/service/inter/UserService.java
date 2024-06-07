package com.kouamfranky.ticketapp.service.inter;

import com.kouamfranky.ticketapp.models.dtos.requests.LoginRequest;
import com.kouamfranky.ticketapp.models.dtos.requests.UserRequestDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.TicketResponseDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserInfosDTO;
import com.kouamfranky.ticketapp.models.dtos.responses.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:55
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.service.inter
 **/
public interface UserService {
    Page<UserResponseDTO> getAllUsers(String token, Pageable pageable);

    UserResponseDTO addUser(UserRequestDTO dto);

    UserResponseDTO updateUser(UserRequestDTO dto, Long idUser);



    Page<TicketResponseDTO> findTicketsUsers(Long idUser, String token, Pageable pageable);

    UserInfosDTO processUserLogin(LoginRequest loginRequest);
}
