package vn.elca.training.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.InternalParseException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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

        if(status == null){
            try {
                Long id = Long.parseLong(criteria);
                return new JPAQuery<Project>(em)
                        .from(QProject.project)
                        .where(QProject.project.id.eq(id))
                        .fetch();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
//                .innerJoin(QProject.project.group, QGroup.group).fetchJoin()
                    .where(QProject.project.name.equalsIgnoreCase(criteria).or(QProject.project.customer.equalsIgnoreCase(criteria)))
                    .fetch();
        }
        else{
            try {
                Long id = Long.parseLong(criteria);
                return new JPAQuery<Project>(em)
                        .from(QProject.project)
                        .where(QProject.project.id.eq(id).and(QProject.project.status.eq(status)))
                        .fetch();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
//                .innerJoin(QProject.project.group, QGroup.group).fetchJoin()
                    .where((QProject.project.name.equalsIgnoreCase(criteria)
                            .or(QProject.project.customer.equalsIgnoreCase(criteria)))
                            .and(QProject.project.status.eq(status)))
                    .fetch();
        }
    }

    @Override
    public List<Project> findById(Long id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public void createNewProject(Project project) {
        this.projectRepository.save(project);
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
    public void updateProject(Long id, Project crrProject) {
        Project newProject = projectRepository.findOne(id);
        newProject = crrProject;
        projectRepository.save(newProject);
    }
}
