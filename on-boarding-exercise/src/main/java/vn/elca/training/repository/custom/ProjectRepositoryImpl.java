package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRespositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findProjectById(Long id) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .innerJoin(QProject.project.group, QGroup.group).fetchJoin()
                .where(QProject.project.id.eq(id))
                .fetch();
    }
}
