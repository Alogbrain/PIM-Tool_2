package vn.elca.training.model.dto;

import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.StatusProject;

import java.time.LocalDate;

/**
 * @author gtn
 */
public class ProjectDto {
    private Long id;
    private Integer projectNumber;
    private String name;
    private StatusProject status;
    private String members;
    private String customer;
    private String startDate;
    private GroupDto group;
    private String endDate;
    private Integer version;

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

    public StatusProject getStatus() {
        return status;
    }

    public void setStatus(StatusProject status) {
        this.status = status;
    }

    public GroupDto getGroup() {
        return group;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", projectNumber=" + projectNumber +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", members='" + members + '\'' +
                ", customer='" + customer + '\'' +
                ", startDate='" + startDate + '\'' +
                ", group=" + group +
                ", endDate='" + endDate + '\'' +
                ", version=" + version +
                '}';
    }

    public void setGroup(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        this.group = groupDto;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


}
