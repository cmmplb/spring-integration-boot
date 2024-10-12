package com.cmmplb.canal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.core.beans.SelectVO;
import java.util.stream.Collectors;
import io.github.cmmplb.core.exception.CustomException;
import com.cmmplb.canal.dao.TestMapper;
import com.cmmplb.canal.dto.TestDTO;
import com.cmmplb.canal.dto.TestQueryDTO;
import com.cmmplb.canal.entity.Test;
import com.cmmplb.canal.service.TestService;
import com.cmmplb.canal.vo.TestVO;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public Boolean saveInfo(TestDTO dto) {
        Test test = new Test();
        BeanUtils.copyProperties(dto, test);
        return baseMapper.insert(test) > 0;
    }

    @Override
    public Boolean deleteById(Long id) {
        Test testDb = baseMapper.selectById(id);
        if (null == testDb) {
            throw new CustomException("信息不存在");
        }
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean updateInfoById(TestDTO dto) {
        Test testDb = baseMapper.selectById(dto.getId());
        if (null == testDb) {
            throw new CustomException("信息不存在");
        }
        Test test = new Test();
        BeanUtils.copyProperties(dto, test);
        test.setId(dto.getId());
        return baseMapper.updateById(test) > 0;
    }

    @Override
    public List<SelectVO> getList() {
        LambdaQueryWrapper<Test> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Test> testList = baseMapper.selectList(lambdaQueryWrapper);
        return testList.stream().map(
                test -> {
                    SelectVO vo = new SelectVO();
                    vo.setProp(test.getId());
                    vo.setLabel(test.getName());
                    return vo;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public Page<TestVO> getByPaged(TestQueryDTO dto) {
        return baseMapper.selectByPaged(new Page<TestVO>(dto.getCurrent(), dto.getSize()), dto);
    }
}