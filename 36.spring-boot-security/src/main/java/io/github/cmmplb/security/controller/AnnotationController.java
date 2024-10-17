package io.github.cmmplb.security.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.security.domain.convert.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-20 20:10:46
 * @since jdk 1.8
 */

@Slf4j
@Tag(name = "注解权限管理")
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @Operation(summary = "查询注解控制")
    @ApiOperationSupport(order = 1)
    @GetMapping("/info")
    // 角色校验，@Secured注解源自Spring Security框架，它通过指定一个或多个“角色”来限制对方法的访问。当用户尝试访问一个被@Secured注解的方法时，
    // Spring Security将检查用户是否具备注解中指定的任何一个角色。如果用户不具备所需角色，那么访问将被拒绝，并返回一个HTTP 403 Forbidden响应。
    @Secured({"admin"})
    // @RolesAllowed注解用于指定允许访问某个方法或资源的角色，拥有admin角色或user角色都可以访问
    @RolesAllowed({"admin", "user"})
    // 访问方法之前进行权限认证，有其中任意一个权限可以访问对应资源，hasRole()的权限名称需要用 "ROLE_" 开头，而hasAuthority()不需要
    @PreAuthorize("hasAnyAuthority('read','write')")
    public Result<String> getInfo() {
        return ResultUtil.success("注解控制");
    }

    @Operation(summary = "postAuthorize")
    @ApiOperationSupport(order = 1)
    @GetMapping("/postAuthorize")
    // 在访问控制器中的相关方法之后（方法的return先不访问）
    @PostAuthorize("hasAnyAuthority('write')")
    public Result<String> postAuthorize() {
        log.info("postAuthorize方法执行了，如果权限不足，则不会执行return");
        return ResultUtil.success("注解控制");
    }

    @Operation(summary = "postFilter")
    @ApiOperationSupport(order = 1)
    @GetMapping("/postFilter")
    // 返回值必须是集合，移除使对应表达式即的结果为false的元素，filterObject是使用@PreFilter和@PostFilter时的一个内置表达式，表示集合中的当前对象。
    @PostFilter(value = "filterObject.username == 'user'")
    public List<UserDetails> all() {
        List<UserDetails> list = new ArrayList<>();
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername("admin");
        userDetails.setPassword("123456");
        list.add(userDetails);

        userDetails = new UserDetails();
        userDetails.setUsername("user");
        userDetails.setPassword("123456");
        list.add(userDetails);

        return list;
    }


}
