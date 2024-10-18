package io.github.cmmplb.data.jpa.dao;

import io.github.cmmplb.data.jpa.entity.Account;
import io.github.cmmplb.data.jpa.vo.AccountInfoVO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-11 13:42:00
 * @since jdk 1.8
 */
public interface AccountDao {

    List<Tuple> selectList4Dsl();

    Page<Account> selectPaged4Dsl();

    AccountInfoVO selectInfoById4Dsl(Long id);
}
