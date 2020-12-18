package com.laurenemick.pintreach.models;

import javax.validation.constraints.Email;

public class UserMinimum
{
    private String username;

    private String password;

    @Email
    private String primaryemail;

    private String imageurl;

    public UserMinimum()
    {
    }

    public UserMinimum(
        String username,
        String password,
        String primaryemail,
        String imageurl)
    {
        this.username = username;
        this.password = password;
        this.primaryemail = primaryemail;
        this.imageurl = imageurl;
    }

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

    public String getImageurl()
    {
        return imageurl;
    }

    public void setImageurl(String imageurl)
    {
        this.imageurl = imageurl;
    }
}