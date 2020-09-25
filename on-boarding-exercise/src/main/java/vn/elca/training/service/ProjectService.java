package vn.elca.training.service;

import java.util.List;

import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.StatusProject;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    List<Project> findAll();
    List<Project> findByCriteria(String name, StatusProject status);
    List<Project> findById(Long id);
    void createNewProject(Project project);
    void deleteProject(Integer id);
    void updateProject(Long id, Project project);
}
