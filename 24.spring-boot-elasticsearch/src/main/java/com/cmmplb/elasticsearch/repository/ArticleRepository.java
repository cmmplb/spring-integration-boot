package com.cmmplb.elasticsearch.repository;

import com.cmmplb.elasticsearch.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author penglibo
 * @date 2021-12-20 14:29:47
 * @since jdk 1.8
 */

public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"title.keyword\" : \"?\"}}}}")
    Page<Article> findByTitle(String title, Pageable pageable);

    //默认的注释
    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
    Page<Article> findByContent(String content, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"type.keyword\" : \"?\"}}}}")
    Page<Article> findByType(Integer type, Pageable pageable);


}
