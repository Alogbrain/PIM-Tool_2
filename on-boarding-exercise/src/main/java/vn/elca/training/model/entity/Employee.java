package vn.elca.training.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String visa;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Date birthDate;
    @Version
    @Column(nullable = false, columnDefinition ="int DEFAULT 0")
    private Integer version;

    //    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private Group group;
    @OneToMany(mappedBy = "groupLeader")
    private Set<Group> group = new HashSet<>();
    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();

    public Employee(String visa, Set<Group> group, Set<Project> projects) {
        this.visa = visa;
        this.group = group;
        this.projects = projects;
    }

    public Employee(String visa) {
        this.visa = visa;
    }

    public Employee() {
    }

    public Employee(Long id, String visa, String firstName, String lastName, Date birthDate, Integer version, Set<Group> group, Set<Project> projects) {
        this.id = id;
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.version = version;
        this.group = group;
        this.projects = projects;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public Set<Group> getGroup() {
        return group;
    }

    public void setGroup(Set<Group> group) {
        this.group = group;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    //    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "employee_role",
//        joinColumns = @JoinColumn(name = "employee_id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;
}
