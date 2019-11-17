package com.lambdaschool.usermodel.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends Auditable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectid;

    @Column(nullable = false)
    private String projectname;

    @Column(nullable = false)
    private String description;

    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties("projects")
    private User user;

//    @Column
//    @ElementCollection(targetClass=String.class)
//    private List<String> materials;
//
//    @Column
//    @ElementCollection(targetClass=String.class)
//    private List<String> steps;

    public Project() {}

    public Project(String projectname, String description, String photoUrl, User user) {
        this.projectname = projectname;
        this.description = description;
        this.photoUrl = photoUrl;
        this.user = user;
    }

    public Project(String projectName, User user, String description, String photoUrl, List<String> materials, List<String> steps) {
        this.projectname = projectName;
        this.user = user;
        this.description = description;
        this.photoUrl = photoUrl;
//        this.materials = materials;
//        this.steps = steps;
    }


    public long getProjectId() {
        return projectid;
    }

    public void setProjectId(long projectId) {
        this.projectid = projectId;
    }

    public String getProjectName() {
        return projectname;
    }

    public void setProjectName(String projectName) {
        this.projectname = projectName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

//    public List<String> getMaterials() {
//        return materials;
//    }
//
//    public void setMaterials(List<String> materials) {
//        this.materials = materials;
//    }
//
//    public List<String> getSteps() {
//        return steps;
//    }
//
//    public void setSteps(List<String> steps) {
//        this.steps = steps;
//    }
}