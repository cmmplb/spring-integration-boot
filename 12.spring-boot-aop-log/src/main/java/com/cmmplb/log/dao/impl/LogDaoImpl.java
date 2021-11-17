package com.cmmplb.log.dao.impl;

import com.cmmplb.log.dao.LogDao;
import com.cmmplb.log.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penglibo
 * @date 2021-04-14 14:49:20
 */

@Repository
public class LogDaoImpl implements LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveLog(Log log) {
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        String sql = "insert into `sys_log` " + "(`time`,`method`,`params`,`ip`,`type`,`exc_cause`,`exc_desc`,`exc_location`,`create_time`) " +
                "values(:time,:method,:params,:ip,:type,:excCause,:excDesc,:excLocation,:createTime)";
        npjt.update(sql, new BeanPropertySqlParameterSource(log));
    }
}
