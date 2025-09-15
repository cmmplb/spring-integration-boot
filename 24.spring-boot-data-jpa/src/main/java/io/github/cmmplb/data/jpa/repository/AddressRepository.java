package io.github.cmmplb.data.jpa.repository;

import io.github.cmmplb.data.jpa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-09 14:24:18
 * @since jdk 1.8
 * 参数一 T :当前需要映射的实体
 * 参数二 ID :当前映射的实体中的ID的类型
 */

public interface AddressRepository extends JpaRepository<Address, Long>, Serializable {

}
