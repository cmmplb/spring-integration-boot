package io.github.cmmplb.data.jpa.service.impl;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.data.jpa.dao.AccountDao;
import io.github.cmmplb.data.jpa.repository.AccountRepository;
import io.github.cmmplb.data.jpa.entity.Account;
import io.github.cmmplb.data.jpa.service.AccountService;
import io.github.cmmplb.data.jpa.vo.AccountInfoVO;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean save(Account account) {
        account.setCreateTime(new Date());
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        accountRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Account> getList() {
        return accountRepository.findAll();
    }

    @Override
    public List<Tuple> getList4Dsl() {
        return accountDao.selectList4Dsl();
    }

    @Override
    public Page<Account> getPaged4Dsl() {
        return accountDao.selectPaged4Dsl();
    }

    @Override
    public AccountInfoVO getInfoById4Dsl(Long id) {
        return accountDao.selectInfoById4Dsl(id);
    }

    @Override
    public PageResult<Account> getByPaged(QueryPageBean queryPageBean) {
        // PageRequest第一页的pageNumber是0
        Page<Account> page = accountRepository.findAll(PageRequest.of(queryPageBean.getCurrent() - 1, queryPageBean.getSize(), Sort.Direction.DESC, "id"));
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }
}
