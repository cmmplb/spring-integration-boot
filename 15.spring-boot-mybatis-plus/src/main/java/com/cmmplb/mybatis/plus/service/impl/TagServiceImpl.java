package com.cmmplb.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmmplb.mybatis.plus.dao.TagMapper;
import com.cmmplb.mybatis.plus.entity.Tag;
import com.cmmplb.mybatis.plus.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author penglibo
* @date 2021-08-22 11:47:03
* @since jdk 1.8
*/
    
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
