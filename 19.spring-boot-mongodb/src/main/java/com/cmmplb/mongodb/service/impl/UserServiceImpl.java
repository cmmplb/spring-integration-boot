package com.cmmplb.mongodb.service.impl;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.utils.StringUtil;
import com.cmmplb.mongodb.dao.UserDao;
import com.cmmplb.mongodb.dto.UserPageQueryDTO;
import com.cmmplb.mongodb.entity.User;
import com.cmmplb.mongodb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.LongSupplier;

/**
 * @author penglibo
 * @date 2021-09-14 10:06:37
 * @since jdk 1.8
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MongoTemplate template;

    @Override
    public boolean save(User user) {
        log.info("save:{}", userDao.save(user));
        return true;
    }

    @Override
    public boolean removeById(String id) {
        userDao.deleteById(id);
        return true;
    }

    @Override
    public boolean updateById(User user) {
        // 修改也是新增，id不为空就是修改，否则就是新增
        log.info("save:{}", userDao.save(user));
        return true;
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    public User getById(String id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public List<User> condition(UserPageQueryDTO dto) {
        return userDao.findFirstByNameLikeOrSexEquals(dto.getName(), dto.getSex());
    }

    @Override
    public PageResult<User> page(UserPageQueryDTO dto) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (!StringUtil.isEmpty(dto.getName())) {
            criteria.and(User.COLUMN_NAME).is(dto.getName());
        }
        if (null != dto.getSex()) {
            criteria.and(User.COLUMN_SEX).is(dto.getSex());
        }
        if (null != dto.getBirthday()) {
            criteria.and(User.COLUMN_BIRTHDAY).gt(dto.getBirthday());
        }
        query.addCriteria(criteria);
        Sort sort = Sort.by(Sort.Direction.DESC, User.COLUMN_BIRTHDAY);
        // 当前页从0开始，需要减去1
        Pageable pageable = PageRequest.of(dto.getCurrent() - 1, dto.getSize()/*, sort*/);

        LongSupplier longSupplier = () -> template.count(query, User.class);
        if (0 == longSupplier.getAsLong()) {
            return new PageResult<>();
        }
        List<User> users = template.find(query.with(pageable), User.class);
        Page<User> page = PageableExecutionUtils.getPage(users, pageable, () -> template.count(query, User.class));

        return new PageResult<>(longSupplier.getAsLong(), users);

    }
}
