package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUser() {
        List<User> useList = userRepository.findAll(Sort.by("firstName"));
        List<UserDTO> userDTOList = useList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public UserDTO findByUserName(String userName) {
        return userMapper.convertToDto(userRepository.findByUserName(userName));
    }

    @Override
    public void save(UserDTO user) {

        userRepository.save(userMapper.convertToEntity(user));

    }

    @Override
    public UserDTO update(UserDTO user) {
        //find current user
        User user1 = userRepository.findByUserName(user.getUserName());//has id
        //map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user);//no id
        //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the DB
       userRepository.save(convertedUser);
        return findByUserName(user.getUserName());
    }

    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);

    }

    @Override
    public void delete(String userName) {
        //go to db and get that user with useName
        User user = userRepository.findByUserName(userName);
        //change the isDeleted field to true
        user.setIsDeleted(true);
        //save the object in the db
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> userList = userRepository.findByRoleDescriptionIgnoreCase(role);
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}
