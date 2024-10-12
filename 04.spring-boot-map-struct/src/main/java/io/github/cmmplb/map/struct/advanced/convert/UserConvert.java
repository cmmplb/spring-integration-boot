package com.cmmplb.map.struct.advanced.convert;

import com.cmmplb.map.struct.advanced.core.converter.Converter;
import com.cmmplb.map.struct.advanced.dto.UserDTO;
import com.cmmplb.map.struct.advanced.entity.User;
import com.cmmplb.map.struct.advanced.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-03 16:56:25
 * @since jdk 1.8
 */

@Mapper
public interface UserConvert extends Converter<User, UserVO> {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 重写添加规则,
     * @param user user
     * @return vo
     */
    @Override
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
    })
    UserVO convert(User user);

    // 集合默认
}
