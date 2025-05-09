package io.github.cmmplb.activiti.service;

import io.github.cmmplb.activiti.vo.JobVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;

/**
 * @author penglibo
 * @date 2023-11-20 15:59:36
 * @since jdk 1.8
 */
public interface ManagementService {

    PageResult<JobVO> getJobByPaged(Byte type, QueryPageBean queryPageBean);
}
