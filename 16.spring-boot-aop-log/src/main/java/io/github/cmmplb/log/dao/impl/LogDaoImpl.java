package io.github.cmmplb.log.dao.impl;

import io.github.cmmplb.log.dao.LogDao;
import io.github.cmmplb.log.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

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
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
        String sql = "insert into `sys_log`(" +
                    " `status`" +
                    ",`type`" +
                    ",business_type" +
                    ",content" +
                    ",ip" +
                    ",user_agent" +
                    ",request_uri" +
                    ",`method`" +
                    ",method_name" +
                    ",params" +
                    ",`time`" +
                    ",exc_cause" +
                    ",exc_desc" +
                    ",exc_location" +
                    ",create_time" +
                ") values (" +
                    " :status" +
                    ",:type" +
                    ",:businessType" +
                    ",:content" +
                    ",:ip" +
                    ",:userAgent" +
                    ",:requestUri" +
                    ",:method" +
                    ",:methodName" +
                    ",:params" +
                    ",:time" +
                    ",:excCause" +
                    ",:excDesc" +
                    ",:excLocation" +
                    ",:createTime" +
                ")";
        npjt.update(sql, new BeanPropertySqlParameterSource(log));
    }
}
