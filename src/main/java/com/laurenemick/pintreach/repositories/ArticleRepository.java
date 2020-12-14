package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long>
{
}
