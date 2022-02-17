package com.cmmplb.data.jpa.service.impl;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.data.jpa.dao.AccountDao;
import com.cmmplb.data.jpa.entity.*;
import com.cmmplb.data.jpa.service.AccountService;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-09 14:26:04
 * @since jdk 1.8
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @PersistenceContext
    private EntityManager em;

    private QAccount account = QAccount.account; // compile生成target  Q类
    private QTag tag = QTag.tag;

    @Override
    public boolean save(Account account) {
        accountDao.save(account);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        accountDao.deleteById(id);
        return true;
    }

    @Override
    public List<Account> getList() {
        return accountDao.findAll();
    }

    @Override
    public List<Tuple> getList4dsl() {
        JPAQuery<Tuple> query = new JPAQuery<Tuple>(em);
        return query.select(account.id,tag.accountId,tag.id,tag.name)
                .from(account,tag)
                .where(account.id.eq(tag.accountId)).fetch();
    }

    @Override
    public PageResult<Account> getByPaged(QueryPageBean queryPageBean) {
        Page<Account> page = accountDao.findAll(PageRequest.of(queryPageBean.getCurrent(), queryPageBean.getSize(), Sort.Direction.DESC, "id"));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }
}
