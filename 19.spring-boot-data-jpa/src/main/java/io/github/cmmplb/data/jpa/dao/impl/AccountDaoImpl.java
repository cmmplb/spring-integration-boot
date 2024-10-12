package com.cmmplb.data.jpa.dao.impl;

import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.data.jpa.dao.AccountDao;
import com.cmmplb.data.jpa.entity.Account;
import com.cmmplb.data.jpa.entity.QAccount;
import com.cmmplb.data.jpa.entity.QCategory;
import com.cmmplb.data.jpa.entity.QTag;
import com.cmmplb.data.jpa.vo.AccountInfoVO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-11 13:39:36
 * @since jdk 1.8
 */

@Repository
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * compile生成target  Q类
     */
    private final QAccount account = QAccount.account;
    public static final QAccount qAccount1 = new QAccount("a1");
    public static final QAccount qAccount2 = new QAccount("a2");
    public static final QCategory qCategory1 = new QCategory("q1");
    public static final QCategory qCategory2 = new QCategory("q2");
    private final QTag tag = QTag.tag;

    @Override
    public List<Tuple> selectList4Dsl() {
        JPAQuery<Tuple> query = new JPAQuery<Tuple>(em);
        return query.select(account.id, tag.accountId, tag.id, tag.name, tag.name.stringValue())
                .from(account, tag)
                .where(account.id.eq(tag.accountId)).fetch();
    }

    @Override
    public Page<Account> selectPaged4Dsl() {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setSize(10);
        queryPageBean.setCurrent(1);

        JPAQuery<Account> where = new JPAQuery<>(em)
                .select(account)
                .from(account);
        if (StringUtils.isNotBlank("name")) {
            where.where(account.name.like("张三"));
        }
        Pageable pageable;
        if (queryPageBean.getCurrent() == 0) { // null
            pageable = PageRequest.of(0, Integer.MAX_VALUE);
        } else {
            int page = queryPageBean.getCurrent() - 1;
            page = Math.max(page, 0);
            pageable = PageRequest.of(page, queryPageBean.getSize());
        }
        QueryResults<Account> results = where
                .orderBy(account.createTime.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<Account>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public AccountInfoVO selectInfoById4Dsl(Long id) {
        // *********引用Mysql函数*********

        // 此处作为查询条件
        StringTemplate stringTemplate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", account.createTime, "%Y-%m-%d 00:00:00");

        // 字符串的创建时间转换,可以通过占位符写,此处作为条件
        // BooleanExpression startDateBoolean = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", account.createTime, "%Y-%m-%d 00:00:00")
        //         .loe(DateTimePath.currentTimestamp().stringValue());
        // BooleanExpression endDateBoolean = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", account.createTime, "%Y-%m-%d 23:59:59")
        //         .goe(DateTimePath.currentTimestamp().stringValue());
        // 此处作为条件拼接到wehere
        // query.where(startDateBoolean.and(endDateBoolean));

        // BooleanExpression startDateBoolean = Expressions.stringTemplate("DATE_FORMAT(" + account.createTime + "}, '%Y-%m-%d 00:00:00')" ).loe(DateTimePath.currentTimestamp().stringValue());

        // query.where(account.createTime.loe(DateTimePath.currentTimestamp().stringValue())
        //         .and(account.createTime.goe(DateTimePath.currentTimestamp().stringValue())));

        JPAQuery<AccountInfoVO> query = new JPAQuery<>(em);
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        // 联查两张相同表qAccount1, qAccount2
        JPQLQuery<String> subQuery = JPAExpressions.select(qAccount1.name.as("accountName")).from(qAccount1);

        JPAQuery<AccountInfoVO> select;
        // *************使用bean投影嵌套子查询*************失败
        // select = query.select(
        //         // bean投影, 可以自动装箱到对象里面, 对应字段取别名
        //         Projections.bean(AccountInfoVO.class
        //                 , account.id
        //                 , account.name
        //                 , account.password
        //                 , account.createTime
        //                 , tag.id.as("tagId")
        //                 , tag.name.as("tagName")
        //                 // 嵌套子查询
        //                 , subQuery
        //         )
        // );

        // *************使用fields投影嵌套子查询*************失败
        // select = query.select(
        //         // fields投影可以自动装箱到对象里面, 对应字段取别名
        //         // , 构造器方式可以映射
        //         Projections.fields(AccountInfoVO.class
        //                 , account.id
        //                 , account.name
        //                 , account.password
        //                 , account.createTime
        //                 , tag.id.as("tagId")
        //                 , tag.name.as("tagName")
        //                 , stringTemplate.as("formatCreateTime")
        //                 // 嵌套子查询
        //                 , (subQuery)
        //         )
        // );

        // *************使用构造器投影嵌套子查询*************成功
        select = query.select(
                // 构造器投影,这种构造器方式, 只支持对数值和String类型的映射处理, 当你定义了Date等等类型, 需要在构造函数转换string为对象类型
                // 可以自动装箱到对象里面, 对应字段取别名
                Projections.constructor(AccountInfoVO.class
                        , account.id
                        , account.name
                        , account.password
                        , account.createTime
                        , tag.id.as("tagId")
                        , tag.name.as("tagName")
                        , stringTemplate.as("formatCreateTime")
                        // 嵌套子查询
                        , subQuery
                        ,qCategory1.name
                        ,qCategory2.name
                )
        );
        JPAQuery<AccountInfoVO> where =
                select
                        .from(account)
                        .leftJoin(tag).on(tag.accountId.eq(account.id))
                        .leftJoin(qCategory1).on(account.categoryOneId.eq(qCategory1.id))
                        .leftJoin(qCategory2).on(account.categoryTwoId.eq(qCategory2.id))
                ;
        // 这里可以把筛选条件提取出来
        where.where(account.id.eq(id));
        subQuery.where(qAccount1.id.eq(id));
        return query.fetchOne();

        // *************使用Tuple嵌套子查询*************成功
        // JPAQuery<Tuple> jpaQuery = new JPAQuery<>(em);
        // jpaQuery.select(
        //         // fields投影可以自动装箱到对象里面, 对应字段取别名
        //         // , 构造器方式可以映射
        //         account.id
        //         , account.name
        //         , account.password
        //         , account.createTime
        //         , tag.id.as("tagId")
        //         , tag.name.as("tagName")
        //         , stringTemplate.as("formatCreateTime")
        //         // 嵌套子查询,使用Tuple嵌套子查询没报错, 使用上面那种映射关系就报错了. 
        //         , (subQuery)
        // );
        // jpaQuery.from(account).leftJoin(tag).on(tag.accountId.eq(account.id));
        // jpaQuery.where(account.id.eq(id));
        // subQuery.where(qAccount1.id.eq(id));
        // Tuple tuple = jpaQuery.fetchFirst();
        // AccountInfoVO vo = new AccountInfoVO();
        // vo.setId(tuple.get(account.id));
        // // 索引从0开始
        // vo.setName(tuple.get(1, String.class));
        // vo.setPassword(tuple.get(account.password));
        // vo.setCreateTime(tuple.get(account.createTime));
        // vo.setTagId(tuple.get(tag.id));
        // vo.setTagName(tuple.get(tag.name));
        // vo.setFormatCreateTime(tuple.get(6, String.class));
        // vo.setAccountName(tuple.get(7, String.class));
        // return vo;

    }
}
