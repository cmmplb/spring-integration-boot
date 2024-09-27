package com.cmmplb.mybatis.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

// @Configuration
public class TestBeanPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsBeanDefinition(TransactionConfiguration.class.getName())) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(TransactionConfiguration.class.getName());
            //修改bean的角色为spring框架级别的bean
            beanDefinition.setRole(2);
        }

    }
}