package com.lambdaschool.usermodel.models;

import com.lambdaschool.usermodel.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

@Loggable
public class LoginUser {

    @ApiModelProperty(required = true,
            example = "admin")
    private String username;

    @ApiModelProperty(required = true,
            example = "password")
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
