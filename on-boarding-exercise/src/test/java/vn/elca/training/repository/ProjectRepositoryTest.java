package vn.elca.training.repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.*;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value=SpringRunner.class)
@Transactional
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSaveProject(){
        Set<Employee> employees = new HashSet<>();
        employees.add(new Employee("TQP"));
        employees.add(new Employee("HNH"));
        employees.add(new Employee("NQN"));

        Group group = new Group();
        Project project = new Project("EFV",LocalDate.now(),group);

        projectRepository.save(project);
        groupRepository.save(group);

        Assert.assertEquals(1,groupRepository.count());
    }

    @Test
    public void testSaveMultipleProjects(){
        Group group1 = new Group();
        Group group2 = new Group();

        Set<Project> projects_group1 = new HashSet<>();

        projects_group1.add(new Project("EFV",LocalDate.now(),group1));
        projects_group1.add(new Project("CXTRANET",LocalDate.now(),group1));
        projects_group1.add(new Project("CRYSTALBALL",LocalDate.now(),group1));

        Set<Project> projects_group2 = new HashSet<>();
        projects_group2.add(new Project("IOC CLIENT EXTRANET",LocalDate.now(),group2));
        projects_group2.add(new Project("KSTA MIGRATION",LocalDate.now(),group2));



        projectRepository.save(projects_group1);
        groupRepository.save(group1);
        projectRepository.save(projects_group2);
        groupRepository.save(group2);

        Assert.assertEquals(2,groupRepository.count());
        Assert.assertEquals(10,projectRepository.count());
//        Assert.assertEquals(3,groupRepository.findAll().get(1).getProjects().size());
    }

    @Test
    public void testDeleteProject(){
        Group group = new Group();
        Project project = new Project("EFV",LocalDate.now(),group);
        projectRepository.save(project);
        groupRepository.save(group);

        projectRepository.delete(project);
//        groupRepository.delete(group.getId());
        Assert.assertEquals(5,projectRepository.count());
        Assert.assertEquals(1,groupRepository.count());
    }

    @Test
    public void testFindProjectByGroupIdWithQueryDSL(){
        Group group = new Group();

        Set<Project> projects_group1 = new HashSet<>();

        projects_group1.add(new Project("EFV",LocalDate.now(),group));
        projects_group1.add(new Project("CXTRANET",LocalDate.now(),group));
        projects_group1.add(new Project("CRYSTALBALL",LocalDate.now(),group));
        projectRepository.save(projects_group1);
        groupRepository.save(group);

        List<Project> projects =  new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.group.id.eq(1L))
                .fetch();

        Assert.assertEquals(3, projects.size());
    }

    @Test
    public void testFindProjectsWithQueryDSL(){
        final String PROJECT_NAME = "EFV";

        Group group1 = new Group();
        Group group2 = new Group();
        Set<Project> projects_group1 = new HashSet<>();
        projects_group1.add(new Project("EFV",LocalDate.parse("2020-09-09"),group1));
        projects_group1.add(new Project("EFV",LocalDate.parse("2020-09-09"),group2));
        projects_group1.add(new Project("CXTRANET",LocalDate.parse("2020-09-09"),group1));
        projects_group1.add(new Project("CRYSTALBALL",LocalDate.parse("2020-09-09"),group1));

        projectRepository.save(projects_group1);
        groupRepository.save(group1);
        groupRepository.save(group2);

        List<Project> projects =  new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq(PROJECT_NAME)
                        .and(QProject.project.endDate.eq(LocalDate.parse("2020-09-09"))))
                .fetch();

        Assert.assertEquals(2, projects.size());
    }

    @Test
    public void testCountAll() {
        projectRepository.save(new Project("KSTA", LocalDate.now()));
        projectRepository.save(new Project("LAGAPEO", LocalDate.now()));
        projectRepository.save(new Project("ZHQUEST", LocalDate.now()));
        projectRepository.save(new Project("SECUTIX", LocalDate.now()));
        Assert.assertEquals(9, projectRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String PROJECT_NAME = "KSTA";
        projectRepository.save(new Project(PROJECT_NAME, LocalDate.now()));
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq(PROJECT_NAME))
                .fetchFirst();
        Assert.assertEquals(PROJECT_NAME, project.getName());
    }
}
