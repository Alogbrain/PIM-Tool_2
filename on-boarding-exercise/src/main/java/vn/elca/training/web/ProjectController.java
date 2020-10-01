package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDtoForList;
import vn.elca.training.model.exception.*;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.service.EmployeeService;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@RestController
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
    public List<ProjectDtoForList> query() {
        return projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDtoForList::getProjectNumber))
                .collect(Collectors.toList());
    }

    @PostMapping("/search")
    @ResponseBody
    public List<ProjectDtoForList> search(@RequestParam(value = "status", defaultValue = "", required = false)
                                                  StatusProject status, @RequestParam("criteria") String criteria) {
        return ("".equals(criteria) && status == null)
                ? projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDtoForList::getProjectNumber))
                .collect(Collectors.toList())
                : projectService.findByCriteria(criteria, status)
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDtoForList::getProjectNumber))
                .collect(Collectors.toList());
    }

    @GetMapping("/queryById/{id}")
    @ResponseBody
    public ProjectDto queryById(@PathVariable Long id) {
        return Mapper.projectToProjectDtoForOne(projectService.findById(id));
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
    public void create(@RequestBody ProjectDto projectDto)
            throws NumberExistException, StartDateGreaterThanEndDateException, VisaNotFoundException {
        Project project = Mapper.projectDtoToProject(projectDto, employeeService);
        projectService.createNewProject(project);
    }

    @PostMapping("/delete-project")
    @ResponseBody
    public void delete(@RequestParam(value = "id") Integer index) {
        projectService.deleteProject(index);
    }

    @PostMapping("/delete-project-list")
    @ResponseBody
    public void delete(@RequestBody List<ProjectDtoForList> list) throws DeteleProjectNotNewStatusException {
        projectService.deleteProjectsList(list);
    }

    @GetMapping("/projectNumber/{projectNumber}")
    @ResponseBody
    public void checkProjectNumber(@PathVariable Integer projectNumber) throws NumberExistException {
        if (projectService.findByProjectNumber(projectNumber) != null) {
            throw new NumberExistException();
        }
    }

    @PostMapping("/members")
    @ResponseBody
    public void checkMembers(@PathParam(value = "members") String members) throws VisaNotFoundException {
        List<String> allVisa = employeeService.getAllVisa();
        String[] memberArr = members.split(",");
        List<String> memberVisaNotFound = new ArrayList<>();
        for (String member : memberArr) {
            if (member.trim().length() != 3 || !allVisa.contains(member.trim())) {
                memberVisaNotFound.add(member);
            }
        }
        if (!memberVisaNotFound.isEmpty()) {
            throw new VisaNotFoundException(memberVisaNotFound);
        }
    }

    @PostMapping("/update-project/{id}")
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody ProjectDto projectDto)
            throws ConcurrentUpdateException, StartDateGreaterThanEndDateException, VisaNotFoundException {
        Project project = Mapper.projectDtoToProject(projectDto, employeeService);
        project.setId(id);
        projectService.updateProject(project);
    }
}
