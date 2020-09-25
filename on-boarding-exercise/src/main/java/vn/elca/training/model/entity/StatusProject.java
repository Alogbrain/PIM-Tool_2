package vn.elca.training.model.entity;

public enum StatusProject {
    New("New"), Inprogress("Inprogress"), Planned("Planned"), Finished("Finished");

    private String value;
    StatusProject(String value) {
        this.value = value;
    }
};
