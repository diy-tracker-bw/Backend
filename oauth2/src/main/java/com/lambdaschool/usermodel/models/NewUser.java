package com.lambdaschool.usermodel.models;

import com.lambdaschool.usermodel.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

@Loggable
public class NewUser
{
    @ApiModelProperty(required = true,
                      example = "lambdauser")
    private String username;

    @ApiModelProperty(required = true,
                      example = "lambdapass")
    private String password;

    @ApiModelProperty(required = true,
                      example = "lambda@gmail.com")
    private String email;

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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
