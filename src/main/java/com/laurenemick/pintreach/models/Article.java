package com.laurenemick.pintreach.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long articleid;

    private String url;

    private String title;

    private String author;

    private String source;

    private String publishedAt;

    private String urlToImage;

    private String content;

    private String description;

    @OneToMany(mappedBy = "article",
        cascade = CascadeType.ALL)
    @JsonIgnoreProperties("article")
    private List<BoardArticles> boardarticles = new ArrayList<>();

    public Article()
    {
    }

    public Article(
        String url,
        String title,
        String author,
        String source,
        String publishedAt,
        String urlToImage,
        String content,
        String description)
    {
        this.url = url;
        this.title = title;
        this.author = author;
        this.source = source;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
        this.content = content;
        this.description = description;
    }

    public long getArticleid()
    {
        return articleid;
    }

    public void setArticleid(long articleid)
    {
        this.articleid = articleid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage()
    {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage)
    {
        this.urlToImage = urlToImage;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<BoardArticles> getBoardarticles()
    {
        return boardarticles;
    }

    public void setBoardarticles(List<BoardArticles> boardarticles)
    {
        this.boardarticles = boardarticles;
    }
}