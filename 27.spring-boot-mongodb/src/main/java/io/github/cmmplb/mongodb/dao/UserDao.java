package io.github.cmmplb.mongodb.dao;

import io.github.cmmplb.mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-14 10:05:40
 * @since jdk 1.8
 */

@Repository
public interface UserDao extends MongoRepository<User, String> {

    /**
     * 具有jpa特性,直接写条件就行
     * @param name 姓名
     * @param sex 性别
     * @return
     */
    List<User> findFirstByNameLikeOrSexEquals(@Param("name") String name, @Param("sex") Byte sex);
}
