package com.cmmplb.elasticsearch.service;

import com.cmmplb.elasticsearch.entity.Article;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-12-20 14:32:44
 * @since jdk 1.8
 */
public interface ArticleService {

    void createIndex();

    void deleteIndex(String index);

    void save(Article docBean);

    void saveAll(List<Article> list);

    Iterator<Article> findAll();

    Page<Article> findByTitle(String title);

    Page<Article> findByContent(String firstCode);

    Page<Article> findByType(Integer type);

    Page<Article> query(String key);
}
