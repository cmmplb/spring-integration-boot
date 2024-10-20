package io.github.cmmplb.mybatis.service.impl;

import io.github.cmmplb.mybatis.dao.TagMapper;
import io.github.cmmplb.mybatis.entity.Tag;
import io.github.cmmplb.mybatis.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Boolean removeById(Long id) {
        return tagMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean save(Tag tag) {
        return tagMapper.insert(tag) > 0;
    }

    @Override
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Boolean updateById(Tag tag) {
        return tagMapper.updateById(tag) > 0;
    }

}
