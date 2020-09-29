package vn.elca.training.util;

import org.apache.commons.lang3.StringUtils;
import vn.elca.training.model.dto.ProjectDtoForList;
import vn.elca.training.model.exception.VisaNotFoundException;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.EmployeeService;

import java.time.LocalDate;
import java.util.*;

/**
 * @author gtn
 */
public class Mapper {
    public Mapper() {
        // Mapper utility class
    }

    public static ProjectDtoForList projectToProjectDtoForAll(Project entity) {
        ProjectDtoForList dto = new ProjectDtoForList();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCustomer(entity.getCustomer());
        dto.setStartDate(entity.getStartDate().toString());
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
        dto.setVersion(entity.getVersion());
        if (entity.getEndDate() != null) {
            dto.setEndDate(entity.getEndDate().toString());
        } else {
            dto.setEndDate("");
        }
        String employeeArr[] = new String[entity.getEmployees().size()];
        int i = 0;
        for (Employee em : entity.getEmployees()) {
            employeeArr[i++] = em.getVisa();
        }
        String employee = String.join(",", employeeArr);

        dto.setMembers(employee);
        return dto;
    }

    public static Project projectDtoToProject(ProjectDto entity, EmployeeService employeeService) {
        // fetch entity from database
        // detached entity
        // copy data from dto to entity
        Project project = new Project();
        project.setProjectNumber(entity.getProjectNumber());
        project.setName(entity.getName());
        project.setStatus(entity.getStatus());
        project.setCustomer(entity.getCustomer());
        project.setStartDate(LocalDate.parse(entity.getStartDate()));
        if (StringUtils.isNotBlank(entity.getEndDate())) {
            project.setEndDate(LocalDate.parse(entity.getEndDate()));
        }
        project.setGroup(new Group(entity.getGroup().getId()));
        project.setVersion(entity.getVersion());
        if(!StringUtils.isBlank(entity.getMembers())){
            List<String> allVisa = employeeService.getAllVisa();
            String[] members = entity.getMembers().split(",");
            List<String> memberVisaNotFound = new ArrayList<>();
            for (String member : members) {
                if (member.trim().length() != 3 || !allVisa.contains(member.trim())) {
                    memberVisaNotFound.add(member);
                }
            }
            if (!memberVisaNotFound.isEmpty()) {
                throw new VisaNotFoundException(memberVisaNotFound);
            }
            List<Employee> employeeList = employeeService.getEmployeeByVisa(members);
            project.setEmployees(employeeList);
        }else{
            List<Employee> employeeList = new ArrayList<>();
            project.setEmployees(employeeList);
        }




//        System.out.println(project);
        return project;
    }

    public static GroupDto groupToGroupDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        return dto;
    }

    public static EmployeeDto employeeToEmployeeDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setVisa(entity.getVisa());
        return dto;
    }
}
