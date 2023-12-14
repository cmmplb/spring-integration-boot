package com.cmmplb.data.jpa.repository;

import com.cmmplb.data.jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-16 14:55:01
 * @since jdk 1.8
 */

public interface TagRepository extends JpaRepository<Tag, Long>, Serializable, QuerydslPredicateExecutor<Tag> {
}
