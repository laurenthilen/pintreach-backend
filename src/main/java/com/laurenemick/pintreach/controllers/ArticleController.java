package com.laurenemick.pintreach.controllers;

import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController
{
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/articles", produces = {"application/json"})
    public ResponseEntity<?> listAllArticles()
    {
        List<Article> myArticles = articleService.findAll();
        return new ResponseEntity<>(myArticles, HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}",
        produces = {"application/json"})
    public ResponseEntity<?> getArticleById(
        @PathVariable
            Long articleId)
    {
        Article a = articleService.findArticleById(articleId);
        return new ResponseEntity<>(a,
            HttpStatus.OK);
    }

    @PostMapping(value = "/article")
    public ResponseEntity<?> addArticle(@Valid @RequestBody Article newarticle)
    {
        newarticle.setArticleid(0);
        newarticle = articleService.save(newarticle);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newArticleURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{userid}")
            .buildAndExpand(newarticle.getArticleid())
            .toUri();
        responseHeaders.setLocation(newArticleURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

//    // later addition - user can update articles
//    @PutMapping(value = "/article/{articleid}")
//    public ResponseEntity<?> updateArticleById(
//        @RequestBody Article updateArticle,
//        @PathVariable long articleid)
//    {
//        articleService.update(articleid, updateArticle);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @DeleteMapping(value = "/article/{articleid}")
    public ResponseEntity<?> getArticleById(
        @PathVariable long articleid)
    {
        articleService.delete(articleid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}