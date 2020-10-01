package vn.elca.training.service;

import java.util.List;

import vn.elca.training.model.dto.ProjectDtoForList;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.DeteleProjectNotNewStatusException;
import vn.elca.training.model.exception.NumberExistException;
import vn.elca.training.model.exception.StartDateGreaterThanEndDateException;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    List<Project> findAll();
    List<Project> findByCriteria(String name, StatusProject status);
    Project findById(Long id);
    Project findByProjectNumber(Integer projectNumber);
    void createNewProject(Project project) throws NumberExistException, StartDateGreaterThanEndDateException;
    void deleteProject(Integer id);
    void deleteProjectsList(List<ProjectDtoForList> list) throws DeteleProjectNotNewStatusException;
    void updateProject(Project project) throws ConcurrentUpdateException, StartDateGreaterThanEndDateException;
}
