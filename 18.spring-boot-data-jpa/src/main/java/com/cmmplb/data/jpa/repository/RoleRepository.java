package com.cmmplb.data.jpa.repository;

import com.cmmplb.data.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-09 15:11:09
 * @since jdk 1.8
 */

public interface RoleRepository extends JpaRepository<Role, Long>, Serializable {
}
