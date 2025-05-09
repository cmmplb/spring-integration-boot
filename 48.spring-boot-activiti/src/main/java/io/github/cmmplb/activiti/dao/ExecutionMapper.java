package io.github.cmmplb.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.activiti.entity.Execution;
import io.github.cmmplb.activiti.vo.LeaveApplyVO;
import io.github.cmmplb.core.beans.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-20 09:28:28
 * @since jdk 1.8
 */
public interface ExecutionMapper extends BaseMapper<Execution> {

    List<Execution> selectExecutionProcessByPaged();
}
