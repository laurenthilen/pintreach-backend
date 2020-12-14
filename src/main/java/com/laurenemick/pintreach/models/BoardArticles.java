package com.laurenemick.pintreach.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "boardarticles")
public class BoardArticles extends Auditable implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "boardid")
    @JsonIgnoreProperties("boardarticles")
    private Board board;

    @Id
    @ManyToOne
    @JoinColumn(name = "articlesid")
    @JsonIgnoreProperties("boardarticles")
    private Article article;

    public BoardArticles()
    {
    }

    public BoardArticles(
        Board board,
        Article article)
    {
        this.board = board;
        this.article = article;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof BoardArticles))
        {
            return false;
        }
        BoardArticles that = (BoardArticles) o;
        return ((board == null) ? 0 : board.getBoardid()) == ((that.board == null) ? 0 : that.board.getBoardid()) &&
            ((article == null) ? 0 : article.getArticleid()) == ((that.article == null) ? 0 : that.article.getArticleid());
    }

    @Override
    public int hashCode()
    {
        // return Objects.hash(board.getBoardid(), article.getArticleid());
        return 40;
    }
}