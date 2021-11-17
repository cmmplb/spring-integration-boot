package com.cmmplb.mybatis.service;

import com.cmmplb.mybatis.entity.Tag;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

public interface TagService {

    /**
     * 根据id删除标签
     * @param id 主键
     * @return 成功true/失败false
     */
    Boolean removeById(Long id);

    /**
     * 新增标签
     * @param tag 标签对象
     * @return 成功true/失败false
     */
    Boolean save(Tag tag);

    /**
     * 根据id查询标签
     * @param id 主键
     * @return 标签对象
     */
    Tag getById(Long id);

    /**
     * 根据id更新标签
     * @param tag 标签对象
     * @return 成功true/失败false
     */
    Boolean updateById(Tag tag);

}
