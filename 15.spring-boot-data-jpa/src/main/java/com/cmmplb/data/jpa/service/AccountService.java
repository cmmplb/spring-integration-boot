package com.cmmplb.data.jpa.service;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.data.jpa.entity.Account;
import com.querydsl.core.Tuple;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-09 14:25:57
 * @since jdk 1.8
 */
public interface AccountService {

    boolean save(Account account);

    boolean deleteById(Long id);

    List<Account> getList();

    PageResult<Account> getByPaged(QueryPageBean queryPageBean);

    /**
     * 使用dsl方式查询
     * @return
     */
    List<Tuple> getList4dsl();
}
