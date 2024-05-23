package com.cmmplb.canal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.core.beans.SelectVO;
import com.cmmplb.canal.dto.TestDTO;
import com.cmmplb.canal.dto.TestQueryDTO;
import com.cmmplb.canal.entity.Test;
import com.cmmplb.canal.vo.TestVO;
import java.util.List;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

public interface TestService extends IService<Test> {

    Boolean saveInfo(TestDTO dto);

    Boolean deleteById(Long id);

    Boolean updateInfoById(TestDTO dto);

    List<SelectVO> getList();

    Page<TestVO> getByPaged(TestQueryDTO dto);
}