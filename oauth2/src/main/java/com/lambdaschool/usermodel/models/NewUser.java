package com.lambdaschool.usermodel.models;

import com.lambdaschool.usermodel.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

@Loggable
public class NewUser
{
    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(required = true)
    private String primaryemail;

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

    public String getPrimaryemail()
    {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail;
    }
}
