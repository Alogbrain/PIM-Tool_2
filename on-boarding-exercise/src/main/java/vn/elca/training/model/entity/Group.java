package vn.elca.training.model.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"GROUP\"")
public class Group implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column()
    private Integer version;
    //    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
//    private Set<Employee> groupLeader = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "group_leader_id")
    private Employee groupLeader;
    @OneToMany(mappedBy = "group")
    private Set<Project> projects = new HashSet<>();

    public Group(Long id, Employee groupLeader, Set<Project> projects) {
        this.id = id;
        this.groupLeader = groupLeader;
        this.projects = projects;
    }

    public Group(Employee groupLeader, Set<Project> projects) {
        this.groupLeader = groupLeader;
        this.projects = projects;
    }

    public Group() {
    }

    public Group(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(Employee groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}
