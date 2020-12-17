package com.laurenemick.pintreach.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// User is considered the parent entity

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {"roles"})
public class User extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false,
        unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String primaryemail;

    private String imageurl;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<Board> boards = new ArrayList<>();

//    // later addition - user can add articles
//    @OneToMany(mappedBy = "user",
//        cascade = CascadeType.ALL,
//        orphanRemoval = true)
//    @JsonIgnoreProperties(value = "user", allowSetters = true)
//    private List<Article> articles = new ArrayList<>();

    public User()
    {
    }

    public User(
        String username,
        String password,
        String primaryemail,
        String imageurl)
    {
        this.username = username;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
        this.primaryemail = primaryemail;
        this.imageurl = imageurl;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPasswordNoEncrypt(String password) {
        this.password = password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    public List<Board> getBoards()
    {
        return boards;
    }

    public void setBoards(List<Board> boards)
    {
        this.boards = boards;
    }

//    public List<Article> getArticles()
//    {
//        return articles;
//    }
//
//    public void setArticles(List<Article> articles)
//    {
//        this.articles = articles;
//    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles)
        {
            String myRole = "ROLE_" + r.getRole()
                .getName()
                .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
