package vn.elca.training.service.impl;

import java.beans.Expression;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.ProjectDtoForList;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.DeteleProjectNotNewStatusException;
import vn.elca.training.model.exception.NumberExistException;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.entity.StatusProject;
import vn.elca.training.model.exception.StartDateGreaterThanEndDateException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author vlp
 */
@Service
@Transactional(rollbackFor = {ConcurrentUpdateException.class, NumberExistException.class,
        DeteleProjectNotNewStatusException.class, StartDateGreaterThanEndDateException.class})
public class ProjectServiceImpl implements ProjectService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll(Integer index) {
        return projectRepository.findAll(new PageRequest(index,5, new Sort("projectNumber"))).getContent();
    }

    @Override
    public Long getSizeProjects() {
        return new JPAQuery<Long>(em)
                .from(QProject.project)
                .fetchCount();
    }

    @Override
    public List<Project> findByCriteria(String criteria, StatusProject status, Integer index) {

        final Integer LIMIT_PROJECT = 5;
        BooleanExpression expr = null;

        if(StringUtils.isNotBlank(criteria)){
            expr = (QProject.project.name.containsIgnoreCase(criteria))
                    .or(QProject.project.customer.containsIgnoreCase(criteria))
                    .or(QProject.project.projectNumber.stringValue().containsIgnoreCase(criteria));
            if(status != null){
             expr = expr.and(QProject.project.status.eq(status));
            }
        }
        if(StringUtils.isBlank(criteria) && status != null){
            expr = QProject.project.status.eq(status);
        }

        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(expr)
                .offset(index * LIMIT_PROJECT)
                .orderBy(QProject.project.projectNumber.asc())
                .limit(LIMIT_PROJECT)
                .fetch();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public Project findByProjectNumber(Integer projectNumber) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .innerJoin(QProject.project.group, QGroup.group).fetchJoin()
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchOne();
    }

    @Override
    public void createNewProject(Project project) throws NumberExistException, StartDateGreaterThanEndDateException {
        if (project.getEndDate() != null && project.getStartDate().isAfter(project.getEndDate())) {
            throw new StartDateGreaterThanEndDateException();
        }
        Project currProject = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(project.getProjectNumber()))
                .fetchFirst();
        if (currProject == null) {
            this.projectRepository.save(project);
        } else {
            throw new NumberExistException();
        }
    }

    @Override
    public void deleteProject(Integer projectNumber) {
        projectRepository.deleteProjectByProjectNumber(projectNumber);
    }

    @Override
    public void deleteProjectsList(List<ProjectDtoForList> list) throws DeteleProjectNotNewStatusException {
        for (ProjectDtoForList project : list) {
            if (project.getStatus() != StatusProject.New) {
                throw new DeteleProjectNotNewStatusException(project.getProjectNumber());
            }
        }
        for (ProjectDtoForList project : list) {
            projectRepository.deleteProjectByProjectNumber(project.getProjectNumber());
        }
    }

    @Override
    public void updateProject(Project newProject)
            throws ConcurrentUpdateException, StartDateGreaterThanEndDateException {
        if (newProject.getEndDate() != null && newProject.getStartDate().isAfter(newProject.getEndDate())) {
            throw new StartDateGreaterThanEndDateException();
        }
        try {
            projectRepository.save(newProject);
        } catch (OptimisticLockingFailureException e) {
            throw new ConcurrentUpdateException(e);
        }
    }
}
