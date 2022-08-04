package com.cmmplb.map.struct.advanced.service;

import com.cmmplb.map.struct.advanced.vo.UserVO;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:28:11
 * @since jdk 1.8
 */
public interface AdvancedUserService {

    UserVO getOne();

    List<UserVO> getList();
}
