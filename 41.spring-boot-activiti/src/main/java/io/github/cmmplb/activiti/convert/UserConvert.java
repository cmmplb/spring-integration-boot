package com.cmmplb.activiti.convert;

import com.cmmplb.activiti.entity.User;
import com.cmmplb.activiti.vo.UserInfoVO;
import io.github.cmmplb.core.converter.Converter;
import org.mapstruct.Mapper;

/**
 * @author penglibo
 * @date 2022-08-03 16:56:25
 * @since jdk 1.8
 */

@Mapper
public interface UserConvert extends Converter<User, UserInfoVO> {

}
