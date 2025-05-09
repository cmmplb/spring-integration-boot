package io.github.cmmplb.start.service;

import io.github.cmmplb.core.beans.DataMap;
import io.github.cmmplb.start.entity.User;
import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author penglibo
 * @date 2024-10-12 11:18:50
 * @since jdk 1.8
 */

@Service
public class UserService {

    @Value("${name}")
    private String name;

    public User selectById(Integer id) {
        Map<Integer, User> userMap = new DataMap<Integer, User>()
                .set(1, new User(1, "张三"))
                .set(2, new User(2, "李四"));
        return userMap.get(id);
    }

    public void testVoid() {
        System.out.println("没有返回值");
    }

    public String getName(){
        return name;
    }


}
