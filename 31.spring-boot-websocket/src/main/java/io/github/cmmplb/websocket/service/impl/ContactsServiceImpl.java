package io.github.cmmplb.websocket.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.websocket.dao.ContactsMapper;
import io.github.cmmplb.websocket.domain.entity.Contacts;
import io.github.cmmplb.websocket.service.ContactsService;
import io.github.cmmplb.websocket.utils.SecurityUtil;
import io.github.cmmplb.websocket.domain.vo.ContactsInfoVO;
import io.github.cmmplb.websocket.domain.vo.ContactsVO;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2023-12-19 13:56:02
 * @since jdk 1.8
 */
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper, Contacts> implements ContactsService {

    @Override
    public PageResult<ContactsVO> getByPaged(QueryPageBean bean) {
        Long userId = SecurityUtil.getUserId();
        Page<ContactsVO> page = baseMapper.selectByPaged(new Page<>(bean.getCurrent(), bean.getSize()), userId, bean.getKeywords());
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    @Override
    public ContactsInfoVO getInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }
}
