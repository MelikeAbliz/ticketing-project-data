package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        //controller call me, request all RoleDto, so it can show in the drop_down in the ui
        //I need to make a call to db and get all the roles from table
        //go to repository and find a service(method) which gives me the roles from db
        //how I will call any service here? dependency injection

        List<Role> roleList = roleRepository.findAll();//convert Role to RoleDto

        //I have Role entities from DB
        //I need to convert those Role entities to DTOs
        //I need to use modelMapper
        //I already created a class called RoleMapper and there are method for me that make this conversion,DI

        List<RoleDTO> roleDTOList = roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());

        return roleDTOList;

        //when I run and get : (Relying upon circular references is discouraged and they are prohibited by default
        // . Update your application to remove the dependency cycle between beans.) exception
        //go to RoleDtoConverter class add @lazy to roleService
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDto(roleRepository.findById(id).get());
    }
}
