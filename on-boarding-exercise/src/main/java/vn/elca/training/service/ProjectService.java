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
    Project findById(Integer id);
    void createNewProject(Project project);
    void deleteProject(Integer id);
    void updateProject(Integer id, Project project);
}
