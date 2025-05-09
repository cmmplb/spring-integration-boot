package io.github.cmmplb.elasticsearch.service.impl;

import io.github.cmmplb.elasticsearch.service.entity.Article;
import io.github.cmmplb.elasticsearch.service.repository.ArticleRepository;
import io.github.cmmplb.elasticsearch.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-12-20 14:32:52
 * @since jdk 1.8
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    private Pageable pageable = PageRequest.of(0, 10);

    @Override
    public void createIndex() {
        IndexOperations ops = elasticsearchTemplate.indexOps(Article.class);
        if (ops.exists()) {
            ops.delete();
        }
        ops.create();
        ops.refresh();
        ops.putMapping(ops.createMapping());
    }

    @Override
    public void deleteIndex(String index) {
        elasticsearchTemplate.delete(index);
    }

    @Override
    public void save(Article docBean) {
        articleRepository.save(docBean);
    }

    @Override
    public void saveAll(List<Article> list) {
        articleRepository.saveAll(list);
    }

    @Override
    public Iterator<Article> findAll() {
        return articleRepository.findAll().iterator();
    }

    @Override
    public Page<Article> findByTitle(String title) {
        return null;
    }

    @Override
    public Page<Article> findByContent(String content) {
        return articleRepository.findByContent(content, pageable);
    }

    @Override
    public Page<Article> findByType(Integer type) {
        return null;
    }


    @Override
    public Page<Article> query(String key) {
        return articleRepository.findByContent(key, pageable);
    }
}
