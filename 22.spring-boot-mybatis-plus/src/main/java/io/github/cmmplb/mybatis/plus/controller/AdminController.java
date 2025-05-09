package io.github.cmmplb.mybatis.plus.controller;


import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.mybatis.plus.dao.AdminMapper;
import io.github.cmmplb.mybatis.plus.entity.Admin;
import io.github.cmmplb.mybatis.plus.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:50
 */

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/scan/{limit}")
    @Transactional(readOnly = true)
    public void level1sCursor(@PathVariable("limit") int limit) throws IOException {
        try (Cursor<Admin> cursor = adminMapper.scan(limit)) {
            cursor.forEach(i -> log.debug(JSON.toJSONString(i)));
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody Admin user) {
        return ResultUtil.success(adminService.save(user));
    }

    @RequestMapping(value = "/save/batch", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody List<Admin> users) {
        if (!CollectionUtils.isEmpty(users)) {
            return ResultUtil.success(adminService.saveBatch(users));
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(adminService.removeById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody Admin user) {
        return ResultUtil.success(adminService.updateById(user));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Admin>> list() {
        return ResultUtil.success(adminService.list());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Admin> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(adminService.getById(id));
    }
}
