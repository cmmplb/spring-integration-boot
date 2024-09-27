package com.cmmplb.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author penglibo
 * @date 2021-12-20 14:27:06
 * @since jdk 1.8
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "article")
@Setting(shards = 1, replicas = 1, refreshInterval = "1s", indexStoreType = "fs")
public class Article {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    // @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Integer)
    private Integer type;
}
