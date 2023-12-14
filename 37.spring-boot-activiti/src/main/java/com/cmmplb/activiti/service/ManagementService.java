package com.cmmplb.activiti.service;

import com.cmmplb.activiti.vo.JobVO;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;

/**
 * @author penglibo
 * @date 2023-11-20 15:59:36
 * @since jdk 1.8
 */
public interface ManagementService {

    PageResult<JobVO> getJobByPaged(Byte type, QueryPageBean queryPageBean);
}
