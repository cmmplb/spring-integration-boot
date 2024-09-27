package com.cmmplb.elasticsearch.service.impl;

import com.cmmplb.elasticsearch.entity.Article;
import com.cmmplb.elasticsearch.repository.ArticleRepository;
import com.cmmplb.elasticsearch.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
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

    // @Autowired
    // private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    // @Autowired
    // private ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

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

    /**
     * 该方法用于分页查询所有实体，并可选地进行排序。参数Pageable封装了分页和排序的相关信息，包括：
     * 页码 (page)：当前请求的页数，从0开始计数。
     * 每页大小 (size)：每页包含的实体数量。
     * 排序 (Sort)：类似于上一个方法中的Sort参数，定义了排序字段和排序方向。
     * 返回值是Page<T>类型，它不仅包含了当前请求页的所有实体（可通过getContent()方法获取），还提供了   分页相关的元数据，如：
     * 总页数 (totalPages)：基于总实体数和每页大小计算得出的总页数。
     * 总实体数 (totalElements)：数据库中符合条件的实体总数。
     * 是否有下一页 (hasNext)：判断是否还有下一页数据可供查询。
     * 是否有上一页 (hasPrevious)：判断是否还有上一页数据可供查询。
     * 当前页码 (number)：返回当前请求的页码。
     * 每页大小 (size)：返回当前请求的每页大小。
     * 排序信息 (sort)：返回当前请求的排序条件。
     */
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
