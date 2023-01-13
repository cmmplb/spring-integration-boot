package com.cmmplb.start.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author penglibo
 * @date 2022-12-09 16:02:05
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Start {

    @JSONField(name = "startName")
    @JsonProperty("startName")
    private String name;

    private Integer age;

    public static void main(String[] args) {
        String json = "{\n" +
                "\"age\": 15,\n" +
                "\"startName\": \"张三\"\n" +
                "}";
        System.out.println(JSON.parseObject(json,new TypeReference<Start>(){}));
    }
}
