package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends CrudRepository<Article, Long>
{
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE articles SET title = :title, author = :author, content = :content, description = :description, source = :source, publishedAt = :publishedAt, url = :url, urlToImage = :urlToImage, last_modified_by = :uname, last_modified_date = CURRENT_TIMESTAMP where productid = :productid", nativeQuery = true)
//    void updateArticleInformation(String uname,
//                                  long articleid,
//                                  String title,
//                                  String author,
//                                  String content,
//                                  String description,
//                                  String source,
//                                  String publishedAt,
//                                  String url,
//                                  String urlToImage);
}
