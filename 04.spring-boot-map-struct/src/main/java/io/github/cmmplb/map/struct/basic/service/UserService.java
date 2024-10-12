package com.cmmplb.map.struct.basic.service;

import com.cmmplb.map.struct.basic.vo.UserVO;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:28:11
 * @since jdk 1.8
 */
public interface UserService {

    UserVO getOne();

    List<UserVO> getList();

    UserVO getOneMapping();

    UserVO getManyMapping();
}
