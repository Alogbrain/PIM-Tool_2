package vn.elca.training.model.entity;

import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;

/**
 * @author vlp
 *
 */
@Entity
//        attributeNodes = @NamedAttributeNode(value = "tasks", subgraph = "tasks"),
//        subgraphs = @NamedSubgraph(name = "tasks", attributeNodes = @NamedAttributeNode("name")))
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;
    //
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();
    @Column(unique = true, nullable = false)
    @Range(max = 9999, min = 0)
    private Integer projectNumber;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String customer;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProject status;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Version
    @Column(nullable = false, columnDefinition ="int DEFAULT 0")
    private Integer version;



    public Project() {
    }

    public Project(String name, LocalDate endDate) {
        this.name = name;
        this.endDate = endDate;
    }

    public Project(String name, LocalDate endDate, Group group) {
        this.name = name;
        this.group = group;
        this.endDate = endDate;
    }

    public Project(Integer projectNumber, String name, String customer, StatusProject status, LocalDate startDate) {
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
    }

    public Project(Long id, Group group, List<Employee> employees, Integer projectNumber, String name, String customer,
                   StatusProject status, LocalDate startDate, LocalDate endDate, Integer version) {
        this.id = id;
        this.group = group;
        this.employees = employees;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    //
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public StatusProject getStatus() {
        return status;
    }

    public void setStatus(StatusProject status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", group=" + group +
                ", employees=" + employees +
                ", projectNumber=" + projectNumber +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", version=" + version +
                '}';
    }
}
