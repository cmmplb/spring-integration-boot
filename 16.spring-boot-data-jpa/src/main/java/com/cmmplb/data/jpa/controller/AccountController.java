package com.cmmplb.data.jpa.controller;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.data.jpa.entity.Account;
import com.cmmplb.data.jpa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-09 14:27:00
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody Account account) {
        return ResultUtil.success(accountService.save(account));
    }

    @DeleteMapping(value = "/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtil.success(accountService.deleteById(id));
    }

    @GetMapping(value = "/list")
    public Result<List<Account>> getList() {
        return ResultUtil.success(accountService.getList());
    }

    @GetMapping(value = "/paged")
    public Result<PageResult<Account>> getByPaged(QueryPageBean queryPageBean) {
        return ResultUtil.success(accountService.getByPaged(queryPageBean));
    }


}
