package io.github.cmmplb.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.activiti.entity.LeaveApply;
import io.github.cmmplb.activiti.entity.LeaveApplyDate;
import io.github.cmmplb.activiti.vo.LeaveApplyVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

public interface LeaveApplyDateMapper extends BaseMapper<LeaveApplyDate> {

}
