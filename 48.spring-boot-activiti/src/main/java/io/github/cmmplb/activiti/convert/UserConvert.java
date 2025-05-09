package io.github.cmmplb.activiti.convert;

import io.github.cmmplb.activiti.entity.User;
import io.github.cmmplb.activiti.vo.UserInfoVO;
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
