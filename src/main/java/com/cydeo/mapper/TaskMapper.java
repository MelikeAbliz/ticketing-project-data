package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component//why we add this? if any class has a dependency to different class we need to use stereotype annotation
//stereotype role: if one class itself injecting somewhere what is TaskMapper injecting other class,or dependency on one dependency,so we put @Component
public class TaskMapper {
    private final ModelMapper modelMapper;//is a bean, we create in the run class

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO dto) {
        return modelMapper.map(dto, Task.class);
    }

    public TaskDTO convertToDto(Task entity) {
        return modelMapper.map(entity, TaskDTO.class);
    }
}
