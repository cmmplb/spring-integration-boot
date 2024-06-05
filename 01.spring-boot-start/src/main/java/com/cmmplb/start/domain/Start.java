package com.cmmplb.start.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2022-12-09 16:02:05
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Start implements Serializable {

    @JSONField(name = "startName1")
    @JsonProperty("startName2")
    private String name;

    private String age;

    public static void main(String[] args) throws Exception {
        String json = "{\n" +
                "\"age\": 15,\n" +
                "\"startName\": \"张三0\",\n" +
                "\"startName1\": \"张三1\",\n" +
                "\"startName2\": \"张三2\"\n" +
                "}";
        Start start = JSON.parseObject(json, new TypeReference<Start>() {
        });
        System.out.println(JSON.parseObject(json, new TypeReference<Start>() {
        }));
        System.out.println(JSON.toJSONString(start));


        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(start));

        // 打印信息
        // Start(name=张三1, age=15)
        // {"age":15,"startName1":"张三1"}
        // {"age":15,"startName2":"张三1"}
    }
}
