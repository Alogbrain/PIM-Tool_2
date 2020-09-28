package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDtoForList;
import vn.elca.training.model.exception.NumberExistException;
import vn.elca.training.model.exception.StartDateGreaterThanEndDateException;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.service.EmployeeService;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@RestController
@Transactional
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/query")
    @ResponseBody
    public List<ProjectDto> query() {
        return projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDto::getProjectNumber))
                .collect(Collectors.toList());
    }

    @PostMapping("/search")
    @ResponseBody
    public List<ProjectDto> Search(@RequestParam(value = "status", defaultValue = "", required = false)
                                           StatusProject status, @RequestParam("criteria") String criteria) {
        return ("".equals(criteria) && status == null)
                ? projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDto::getProjectNumber))
                .collect(Collectors.toList())
                : projectService.findByCriteria(criteria, status)
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDto::getProjectNumber))
                .collect(Collectors.toList());
    }

    @GetMapping("/queryById/{id}")
    @ResponseBody
    public ProjectDto queryById(@PathVariable Integer id) {
        ProjectDto projectDto = Mapper.projectToProjectDtoForOne(projectService.findById(id));
        System.out.println(projectDto);
        return projectDto;
    }

    @GetMapping("/groups")
    @ResponseBody
    public List<GroupDto> queryGroups() {
        return groupService.findAll()
                .stream()
                .map(Mapper::groupToGroupDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/create-project")
    @ResponseBody
    public void create(@RequestBody ProjectDto projectDto) {
        Project project = Mapper.projectDtoToProject(projectDto, employeeService);
        if (project.getEndDate() != null && project.getStartDate().isAfter(project.getEndDate())) {
            throw new StartDateGreaterThanEndDateException();
        }
        this.projectService.createNewProject(project);
    }

    @PostMapping("/delete-project")
    @ResponseBody
    public void delete(@RequestParam(value = "id") Integer index) {
        this.projectService.deleteProject(index);
    }

    @PostMapping("/delete-project-list")
    @ResponseBody
    public void delete(@RequestBody List<ProjectDtoForList> list) {
        for (ProjectDtoForList pro : list) {
            if (!pro.getStatus().equals(StatusProject.New)) {
                throw new RuntimeException("Project status must be new to delete");
            }
        }
        for (ProjectDtoForList pro : list) {
            this.projectService.deleteProject(pro.getProjectNumber());
        }
    }
    @GetMapping("/projectNumber/{id}")
    @ResponseBody
    public void checkId(@PathVariable Integer id) {
        if(this.projectService.findById(id)!= null){
            throw new NumberExistException();
        }
    }
    @PostMapping("/update-project/{id}")
    @ResponseBody
    public void update(@PathVariable Integer id, @RequestBody ProjectDto projectDto) {
        Project project = Mapper.projectDtoToProject(projectDto, employeeService);
        if (project.getEndDate() != null && project.getStartDate().isAfter(project.getEndDate())) {
            throw new StartDateGreaterThanEndDateException();
        }
        this.projectService.updateProject(id, project);
    }
}
