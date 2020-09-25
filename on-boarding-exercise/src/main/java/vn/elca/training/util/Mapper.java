package vn.elca.training.util;

import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
 */
public class Mapper {
    public Mapper() {
        // Mapper utility class
    }

    public static ProjectDto projectToProjectDtoForAll(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCustomer(entity.getCustomer());
        dto.setStartDate(entity.getStartDate().toString());
//        dto.setGroup(entity.getGroup());
//        dto.setEndDate(entity.getEndDate().toString());
        return dto;
    }

    public static ProjectDto projectToProjectDtoForOne(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCustomer(entity.getCustomer());
        dto.setStartDate(entity.getStartDate().toString());
        dto.setGroup(entity.getGroup());
        dto.setEndDate(entity.getEndDate().toString());
        return dto;
    }
    public static Project projectDtoToProject(ProjectDto entity) {
        Project project = new Project();
        project.setProjectNumber(entity.getProjectNumber());
        project.setName(entity.getName());
        project.setStatus(entity.getStatus());
        project.setCustomer(entity.getCustomer());
        project.setStartDate(LocalDate.parse(entity.getStartDate()));
        project.setEndDate(LocalDate.parse(entity.getEndDate()));
        project.setGroup(new Group(entity.getGroup().getId()));
        return project;
    }
    public static GroupDto groupToGroupDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        return dto;
    }
}
