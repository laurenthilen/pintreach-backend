package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Article;

import java.util.List;

public interface ArticleService
{
    List<Article> findAll();

    Article findArticleById(long id);

    void delete(long id);

    Article save(Article article);
//
//    Article update(
//        long id,
//        Article article);
}