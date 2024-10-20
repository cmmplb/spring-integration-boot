package io.github.cmmplb.activiti.service;

import io.github.cmmplb.activiti.vo.ApplyStatisticsVO;
import io.github.cmmplb.activiti.vo.ItemCountVO;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-12-06 13:47:16
 * @since jdk 1.8
 */
public interface HomeService {

    ItemCountVO getItemCount();

    ApplyStatisticsVO getApplyStatistics(Integer type);
}
