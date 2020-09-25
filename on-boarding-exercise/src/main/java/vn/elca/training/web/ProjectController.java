package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
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
    public List<ProjectDto> Search(@RequestParam(value = "status",defaultValue = "", required = false)
                                               StatusProject status, @RequestParam("criteria") String criteria) {
        return ("".equals(criteria))
                ?projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDto::getProjectNumber))
                .collect(Collectors.toList())
                :projectService.findByCriteria(criteria, status)
                .stream()
                .map(Mapper::projectToProjectDtoForAll)
                .sorted(Comparator.comparing(ProjectDto::getProjectNumber))
                .collect(Collectors.toList());
    }
    @GetMapping("/queryById/{id}")
    @ResponseBody
    public ProjectDto queryById(@PathVariable Long id) {
        ProjectDto projectDto = projectService.findById(id)
                .stream()
                .map(Mapper::projectToProjectDtoForOne)
                .collect(Collectors.toList()).get(0);
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
        Project project = Mapper.projectDtoToProject(projectDto);
        this.projectService.createNewProject(project);
    }
    @PostMapping("/delete-project")
    @ResponseBody
    public void delete(@RequestParam(value = "id") Integer index) {
        this.projectService.deleteProject(index);
    }
    @PostMapping("/update-project/{id}")
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        Project project = Mapper.projectDtoToProject(projectDto);
        project.setId(id);
        this.projectService.updateProject(id, project);
    }
}