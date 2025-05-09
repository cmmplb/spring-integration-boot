package io.github.cmmplb.elasticsearch.service.entity;

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
// @Document + @Setting，第2种方式，使用@Setting的settingPath属性
// @Setting(shards = 1, replicas = 1, refreshInterval = "1s", indexStoreType = "fs")
@Setting(settingPath = "indices/news.setting.json")
// news.setting.json 文件位于 /src/main/resources/indices下
// {
// 	"number_of_shards": 2,
// 	"refresh_interval": "2s",
// 	"analyze": {
// 		"max_token_count": 500
// 	}
// }
public class Article {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Integer)
    private Integer type;
}
