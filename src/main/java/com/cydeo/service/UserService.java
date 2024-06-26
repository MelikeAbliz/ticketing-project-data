package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUser();
    UserDTO findByUserName(String userName);
    void save(UserDTO user);

    UserDTO update(UserDTO user);
    void deleteByUserName(String userName);
    void delete(String userName);

    List<UserDTO> listAllByRole(String role);
}
