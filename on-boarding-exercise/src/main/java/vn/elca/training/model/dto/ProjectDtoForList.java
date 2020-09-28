package vn.elca.training.model.dto;

import vn.elca.training.model.entity.StatusProject;

public class ProjectDtoForList {
    private Long id;
    private Integer projectNumber;
    private String name;
    private StatusProject status;
    private String members;
    private String customer;
    private String startDate;

    @Override
    public String toString() {
        return "ProjectDtoForList{" +
                "id=" + id +
                ", projectNumber=" + projectNumber +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", members='" + members + '\'' +
                ", customer='" + customer + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusProject getStatus() {
        return status;
    }

    public void setStatus(StatusProject status) {
        this.status = status;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
