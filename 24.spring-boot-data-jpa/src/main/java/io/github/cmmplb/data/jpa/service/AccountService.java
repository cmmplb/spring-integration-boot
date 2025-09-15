package io.github.cmmplb.data.jpa.service;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.data.jpa.entity.Account;
import io.github.cmmplb.data.jpa.vo.AccountInfoVO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;

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
    List<Tuple> getList4Dsl();

    Page<Account> getPaged4Dsl();

    AccountInfoVO getInfoById4Dsl(Long id);
}
