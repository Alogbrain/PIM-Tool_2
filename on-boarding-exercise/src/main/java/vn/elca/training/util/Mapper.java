package vn.elca.training.util;

import vn.elca.training.exception.VisaNotFoundException;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.EmployeeService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

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
        String employeeArr[] = new String[entity.getEmployees().size()];
        int i = 0;
        for(Employee em : entity.getEmployees()){
            employeeArr[i++] = em.getVisa();
        }
        String employee = String.join(",", employeeArr);
        dto.setMembers(employee);
        return dto;
    }
    public static Project projectDtoToProject(ProjectDto entity, EmployeeService employeeService) {
        Project project = new Project();
        project.setProjectNumber(entity.getProjectNumber());
        project.setName(entity.getName());
        project.setStatus(entity.getStatus());
        project.setCustomer(entity.getCustomer());
        project.setStartDate(LocalDate.parse(entity.getStartDate()));
        project.setEndDate(LocalDate.parse(entity.getEndDate()));
        project.setGroup(new Group(entity.getGroup().getId()));

        Set<Employee> employeeSet = new HashSet<>();
        List<String> allVisa = employeeService.getAllVisa();
        System.out.println("ALL VISA" + allVisa);
        if(entity.getMembers().contains(",")){
            String [] members = entity.getMembers().split(",");

            for(String member: members){
                if (member.length() != 3 || !allVisa.contains(member)){
                    throw new VisaNotFoundException();
                }else {
                    employeeSet.add(new Employee(member));
                }
            }
        }else{
            String member = entity.getMembers();
            if (member.length() != 3 || !allVisa.contains(member)){
                throw new VisaNotFoundException();
            }
            employeeSet.add(new Employee(member));
        }
        project.setEmployees(employeeSet);

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
