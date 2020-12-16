package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.exceptions.ResourceNotFoundException;
import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.repositories.ArticleRepository;
import com.laurenemick.pintreach.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "articleService")
public class ArticleServiceImpl
    implements ArticleService
{
    @Autowired
    private ArticleRepository articlerepos;

    @Autowired
    private BoardRepository boardrepos;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Article> findAll()
    {
        List<Article> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        articlerepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Article findArticleById(long id)
    {
        return articlerepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article id " + id + " not found!"));
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        articlerepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article id " + id + " not found!"));
        articlerepos.deleteById(id);
        boardrepos.removeBoardWithNoArticles();
    }

    // title, author, content, description, publishedAt, source, url, urlToImage
    @Transactional
    @Override
    public Article save(Article article)
    {
        if (article.getBoardarticles()
            .size() > 0)
        {
            throw new ResourceNotFoundException("Boards are not updated through Articles");
        };

        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setAuthor(article.getAuthor());
        newArticle.setContent(article.getContent());
        newArticle.setDescription(article.getDescription());
        newArticle.setPublishedAt(article.getPublishedAt());
        newArticle.setSource(article.getSource());
        newArticle.setUrl(article.getUrl());
        newArticle.setUrlToImage(article.getUrlToImage());

        return articlerepos.save(article);
    }

//    // title, author, content, description, publishedAt, source, url, urlToImage
//    @Transactional
//    @Override
//    public Article update(long id,
//                          Article article)
//    {
//        if (article.getBoardarticles()
//            .size() > 0)
//        {
//            throw new ResourceNotFoundException("Articles cannot be updated through this process");
//        }
//
//        Article currentArticle = articlerepos.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException("Article id " + id + " not found!"));
//
//        if (article.getTitle() != null)
//        {
//            currentArticle.setTitle(article.getTitle());
//        }
//
//        if (article.getAuthor() != null)
//        {
//            currentArticle.setAuthor(article.getAuthor());
//        }
//
//        if (article.getContent() != null)
//        {
//            currentArticle.setContent(article.getContent());
//        }
//
//        if (article.getDescription() != null)
//        {
//            currentArticle.setDescription(article.getDescription());
//        }
//
//        if (article.getPublishedAt() != null)
//        {
//            currentArticle.setPublishedAt(article.getPublishedAt());
//        }
//
//        if (article.getSource() != null)
//        {
//            currentArticle.setSource(article.getSource());
//        }
//
//        if (article.getUrl() != null)
//        {
//            currentArticle.setUrl(article.getUrl());
//        }
//
//        if (article.getUrlToImage() != null)
//        {
//            currentArticle.setUrlToImage(article.getUrlToImage());
//        }
//
//        articlerepos.updateArticleInformation(userAuditing.getCurrentAuditor()
//                .get(),
//            id,
//            currentArticle.getTitle(),
//            currentArticle.getAuthor(),
//            currentArticle.getContent(),
//            currentArticle.getDescription(),
//            currentArticle.getPublishedAt(),
//            currentArticle.getSource(),
//            currentArticle.getUrl(),
//            currentArticle.getUrlToImage());
//        return findArticleById(id);
//    }
}
