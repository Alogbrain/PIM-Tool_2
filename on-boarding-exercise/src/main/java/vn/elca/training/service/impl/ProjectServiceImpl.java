package vn.elca.training.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.InternalParseException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.exception.NumberExistException;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author vlp
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByCriteria(String criteria, StatusProject status) {

        if (status == null && !criteria.equals("")) {
            try {
                Integer id = Integer.parseInt(criteria);
                return new JPAQuery<Project>(em)
                        .from(QProject.project)
                        .where(QProject.project.projectNumber.eq(id))
                        .fetch();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
                    .where(QProject.project.name.equalsIgnoreCase(criteria).or(QProject.project.customer.equalsIgnoreCase(criteria)))
                    .fetch();
        } else if (status != null && criteria.equals("")) {
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
                    .where(QProject.project.status.eq(status))
                    .fetch();
        } else {
            try {
                Integer id = Integer.parseInt(criteria);
                return new JPAQuery<Project>(em)
                        .from(QProject.project)
                        .where(QProject.project.projectNumber.eq(id).and(QProject.project.status.eq(status)))
                        .fetch();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
                    .where((QProject.project.name.equalsIgnoreCase(criteria)
                            .or(QProject.project.customer.equalsIgnoreCase(criteria)))
                            .and(QProject.project.status.eq(status)))
                    .fetch();
        }
    }

    @Override
    public List<Project> findById(Integer id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public void createNewProject(Project project) {
        Project currProject = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(project.getProjectNumber()))
                .fetchFirst();
        if (currProject == null) {
            this.projectRepository.save(project);
        } else {
            throw new NumberExistException(project.getProjectNumber());
        }
    }

    @Override
    public void deleteProject(Integer id) {
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(id))
                .fetch().get(0);
        projectRepository.delete(project);
    }

    @Override
    public void updateProject(Integer id, Project crrProject) {
        Project newProject =  new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(id))
                .fetchFirst();
        crrProject.setId(newProject.getId());
        newProject = crrProject;
        projectRepository.save(newProject);
    }
}
