package com.cmmplb.activiti.image;

import org.activiti.bpmn.model.BpmnModel;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @author penglibo
 * @date 2022-11-24 09:11:02
 * @since jdk 1.8
 */

public interface ProcessDiagramGenerator extends org.activiti.image.ProcessDiagramGenerator {

    /**
     * 生成流程图
     * @param bpmnModel             模型
     * @param highLightedActivities 高亮已经执行流程节点ID集合
     * @param highLightedFlows      高亮流程已发生流转的线id集合
     * @param activityFontName
     * @param labelFontName
     * @param annotationFontName
     * @param colors                流程图颜色定义，这里固定写死的，[0]new Color(0, 205, 0)-绿色-已经运行后的流程;[1]new Color(255, 0, 0)-红色-当前正在执行的流程;
     * @param activityIds           当前激活的节点
     * @return
     */
    InputStream generateDiagram(BpmnModel bpmnModel,
                                List<String> highLightedActivities,
                                List<String> highLightedFlows,
                                String activityFontName,
                                String labelFontName,
                                String annotationFontName,
                                Color[] colors,
                                Set<String> activityIds);
}
 