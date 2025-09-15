package io.github.cmmplb.canal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.SelectVO;
import io.github.cmmplb.canal.dto.TestDTO;
import io.github.cmmplb.canal.dto.TestQueryDTO;
import io.github.cmmplb.canal.entity.Test;
import io.github.cmmplb.canal.vo.TestVO;
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