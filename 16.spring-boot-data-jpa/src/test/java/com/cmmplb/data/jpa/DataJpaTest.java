package com.cmmplb.data.jpa;

import com.cmmplb.core.utils.RandomUtil;
import com.cmmplb.data.jpa.repository.AccountRepository;
import com.cmmplb.data.jpa.entity.*;
import com.cmmplb.data.jpa.service.AccountService;
import com.cmmplb.data.jpa.vo.AccountTagVO;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

//@RunWith(JUnit4.class)
@SpringBootTest
public class DataJpaTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void getList4Dsl() {
        List<Tuple> list4dsl = accountService.getList4Dsl();
        QTag tag = QTag.tag;
        List<AccountTagVO> list = new ArrayList<>();
        AccountTagVO accountTag;
        for (Tuple tuple : list4dsl) {
            accountTag = new AccountTagVO();
            accountTag.setAccountId(tuple.get(tag.accountId));
            accountTag.setTagId(tuple.get(tag.id));
            accountTag.setTagName(tuple.get(tag.name));
            accountTag.setTagName(tuple.get(4, String.class));
            list.add(accountTag);
        }
        System.out.println(list);
    }

    @Test
    public void getPaged4Dsl() {
        Page<Account> page = accountService.getPaged4Dsl();
        System.out.println(page);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void buildAccountAndAddressData() {
        System.out.println("=================录入账号地址数据=================");
        List<Account> list = new ArrayList<>(2);
        Account account;
        for (int i = 0; i < 2; i++) {
            account = new Account();
            account.setName(RandomUtil.getRandomName());
            account.setPassword(RandomUtil.getRandomString(6));
            account.setCreateTime(new Date());
            List<Address> addressList = new ArrayList<>(5);
            Address address;
            for (int n = 0; n < new Random().nextInt(4); n++) {
                address = new Address();
                address.setName(RandomUtil.getRandomAddress());
                address.setCreateTime(new Date());
                // address.setAccount(account); // 这里要注意把账号设置到地址对象里面去，不然不会把外键的accountId保存到表
                addressList.add(address);
            }
            // account.setAddressList(addressList);
            list.add(account);
        }
        accountRepository.saveAll(list);
        System.out.println("=====================完成===================");
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void buildAccountAndRoleData() {
        System.out.println("=================录入账号角色数据=================");

        List<Account> accountList = new ArrayList<>(2);
        List<Role> roleList = new ArrayList<>(2);
        // 角色数据
        Role role = new Role();
        role.setName("主管");
        role.setCreateTime(new Date());
        // 账号数据
        Account account;
        for (int i = 0; i < 2; i++) {
            account = new Account();
            account.setName(RandomUtil.getRandomName());
            account.setPassword(RandomUtil.getRandomString(6));
            account.setCreateTime(new Date());
            // account.setRoleList(roleList);
            // role.getAccountList().add(account);
            accountList.add(account);
        }
        roleList.add(role);
        role = new Role();
        role.setName("员工");
        role.setCreateTime(new Date());
        for (int i = 0; i < 2; i++) {
            account = new Account();
            account.setName(RandomUtil.getRandomName());
            account.setPassword(RandomUtil.getRandomString(6));
            account.setCreateTime(new Date());
            // account.setRoleList(roleList);
            // role.getAccountList().add(account);
            accountList.add(account);
        }
        roleList.add(role);
        /**
         * 当两方都拥有维护中间表的权力时，中间表的联合主键会重复出现两次或报错
         * 这时有有一方就需要放弃维护权
         */
        // 这里是由role维护关系，就保存role
        // roleDao.saveAll(roleList);
        accountRepository.saveAll(accountList); // 这里保存账号会自动保存角色和关系表
        System.out.println("=====================完成===================");
    }

    @Test
    public void getById() {
        // =====================一对多查询=============================
        // Account account = accountDao.getById(1L);
        // System.out.println(account);
        // System.out.println(account.getAddressList());

        // =====================自定义query=============================
        // System.out.println(accountDao.selectById(1L));

        // System.out.println(accountDao.selectByIdNoNative(1L));

        // =====================查询部分字段dto-不可用=============================
        // System.out.println(accountDao.selectDTOById(1L));

        // =====================用map接受部分字段-可用=============================
        // Map<String, Object> map = accountDao.selectMapById(1L);
        // map.forEach((key, value) -> {
        //     System.out.println("key:" + key + ",value:" + value);
        // });

        // =====================多对多查询=============================
        /*List<Account> accountList = accountDao.findAll();
        for (Account account : accountList) {
            System.out.println("====================");
            System.out.println(account);
            System.out.println(account.getRoleList());
            System.out.println("====================");
        }*/

        // =====================底层方法查询=============================
        /*List<Account> accountList = accountDao.findByName("水焱暨");
         *//**
         *  具体规则 see {@link  org.springframework.data.repository.query.parser.PartTree}
         *//*
        System.out.println(accountList);*/

    }
}
