package io.github.cmmplb.map.struct.basic.convert;

import io.github.cmmplb.map.struct.basic.dto.UserDTO;
import io.github.cmmplb.map.struct.basic.entity.User;
import io.github.cmmplb.map.struct.basic.vo.UserVO;
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
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 转换User
     * @param user user
     * @return vo
     */
    UserVO convert(User user);

    /**
     * 转换User集合
     * @param users users
     * @return List<vo>
     */
    List<UserVO> convertList(List<User> users);

    /**
     * 属性不完全映射转换user
     * @param user user
     * @return user
     */
    @Mappings({
            // 属性不是完全映射的情况,配置相应的映射关系
            @Mapping(source = "userId", target = "id"),
            @Mapping(source = "userName", target = "username"),
    })
    UserVO convert(UserDTO user);

    /**
     * 多属性映射转换user
     * @param dto dto
     * @param user user
     * @return dto
     */
    @Mappings({
            // 属性不是完全映射的情况,配置相应的映射关系
            // 注解还支持多个对象转换为一个对象注解还支持多个对象转换为一个对象
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "dto.password", target = "password"),
    })
    UserVO convert(UserDTO dto, User user);
}
