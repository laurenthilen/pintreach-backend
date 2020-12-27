package com.laurenemick.pintreach.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"user"})
@Table(name = "boards")
public class Board extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long boardid;

    @Column(nullable = false)
    private String title;

    private String description;

    private String thumbnail;

    @OneToMany(mappedBy = "board",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "board",
        allowSetters = true)
    private Set<BoardArticles> articles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = {"boards"}, allowSetters = true)
    private User user;

    public Board()
    {
    }

    public Board(
        String title,
        String description,
        String thumbnail,
        User user)
    {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.user = user;
    }

    public long getBoardid()
    {
        return boardid;
    }

    public void setBoardid(long boardid)
    {
        this.boardid = boardid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public Set<BoardArticles> getArticles()
    {
        return articles;
    }

    public void setArticles(Set<BoardArticles> articles)
    {
        this.articles = articles;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
