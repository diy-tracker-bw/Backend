package com.lambdaschool.usermodel.view;

public interface ProjectsByUserId {

    int getProjectId();

    String getCreatedBy();

    String getCreatedDate();

    String getLastModifiedBy();

    String getModifiedDate();

    String getInstructions();

    String getPhotoURL();

    String getProjectName();

    String getUserId();
}
