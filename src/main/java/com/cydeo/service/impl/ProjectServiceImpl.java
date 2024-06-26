package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects = projectRepository.findAll(Sort.by("projectCode"));
        return projects.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        projectRepository.save(projectMapper.convertToEntity(dto));

    }

    @Override
    public void update(ProjectDTO dto) {
        //find current project
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());//has id
//map update project dto to entity object
        Project convertedProject = projectMapper.convertToEntity(dto);

        //set id to the converted object
        convertedProject.setId(project.getId());
        //set status to the converted object
        convertedProject.setProjectStatus(project.getProjectStatus());
        //save the updated project in the DB
       projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) {
        //go to db and get that project with code
        Project project = projectRepository.findByProjectCode(code);
        //change the isDeleted field to true
        project.setIsDeleted(true);
        //save the object in the db
        projectRepository.save(project);

    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        //hey db give me all the projects assigned to manager login in the system
        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");
        // I need all projects belongs to this manager
        User user = userMapper.convertToEntity(currentUserDTO);

        List<Project> list = projectRepository.findAllByAssignedManager(user);//this list have no two field which projectDto has,so we bring that two field to the list


        return list.stream().map(project -> {
                    ProjectDTO projectDTO = projectMapper.convertToDto(project);//project entity convert to dto
            projectDTO.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));//unfinished task belongs to this project
            projectDTO.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
            return projectDTO;
                }
        ).collect(Collectors.toList());
    }

}

