package com.cmmplb.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.websocket.entity.Contacts;
import com.cmmplb.websocket.vo.ContactsInfoVO;
import com.cmmplb.websocket.vo.ContactsVO;

public interface ContactsService extends IService<Contacts> {

    PageResult<ContactsVO> getByPaged(QueryPageBean bean);

    ContactsInfoVO getInfoById(Long id);
}
