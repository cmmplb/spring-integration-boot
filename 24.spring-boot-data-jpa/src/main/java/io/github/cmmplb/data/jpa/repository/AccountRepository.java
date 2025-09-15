package io.github.cmmplb.data.jpa.repository;

import io.github.cmmplb.data.jpa.dto.AccountDTO;
import io.github.cmmplb.data.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-09 14:24:18
 * @since jdk 1.8
 * 参数一 T :当前需要映射的实体
 * 参数二 ID :当前映射的实体中的ID的类型
 */

public interface AccountRepository extends JpaRepository<Account, Long>, Serializable, QuerydslPredicateExecutor<Account> {

    /**
     * 根据id查询
     * @param id 主键
     * @return account
     */
    @Query(value = "select * from account where id = ?1", nativeQuery = true)
    Account selectById(@Param("id") Long id);

    // @Query(value = "select a from account a where a.id = ?1")
    // Account selectByIdNoNative(@Param("id") Long id);

    @Query(value = "select id,name from account where id = ?", nativeQuery = true)
        // 上面的用这条sql会报错
    Map<String, Object> selectMapById(@Param("id") Long id);

    @Query(value = "select id,name from account where id = ?", nativeQuery = true)
        // 这个也会报错-只能使用上面的map-拿到map再做转换
    AccountDTO selectDTOById(@Param("id") Long id);

    List<Account> findByName(@Param("name") String name);
}
