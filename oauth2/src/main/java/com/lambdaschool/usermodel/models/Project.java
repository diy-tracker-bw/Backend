package com.lambdaschool.usermodel.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends Auditable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectid;

    @ApiModelProperty(required = true)
    @Column(nullable = false)
    private String projectname;

    @ApiModelProperty(required = true)
    @Column(nullable = false,
    columnDefinition = "LONGTEXT")
    private String instructions;

    private String photoUrl;

    private int likes = 0;

    @ManyToOne()
    @JoinColumn(name = "userid",
            nullable = true)
    @JsonIgnoreProperties({"projects", "userroles"})
    private User user;

    public Project() {}

    public Project(String projectname, String instructions, String photoUrl, User user) {
        this.projectname = projectname;
        this.instructions = instructions;
        this.photoUrl = photoUrl;
        this.user = user;
    }

    public Project(String projectname, String instructions) {
        this.projectname = projectname;
        this.instructions = instructions;
    }

    public Project(String projectname, User user, String instructions, String photoUrl, List<String> materials, List<String> steps) {
        this.projectname = projectname;
        this.user = user;
        this.instructions = instructions;
        this.photoUrl = photoUrl;
    }

    public Project(String projectname, String instructions, String photoUrl, int likes, User user) {
        this.projectname = projectname;
        this.instructions = instructions;
        this.photoUrl = photoUrl;
        this.likes = likes;
        this.user = user;
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

    public void setProjectName(String projectname) {
        this.projectname = projectname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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