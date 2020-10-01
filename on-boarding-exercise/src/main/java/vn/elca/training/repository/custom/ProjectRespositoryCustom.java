package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRespositoryCustom {
    Project findProjectById(Long id);
}
