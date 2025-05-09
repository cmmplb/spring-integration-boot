package io.github.cmmplb.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.websocket.domain.entity.Contacts;
import io.github.cmmplb.websocket.domain.vo.ContactsInfoVO;
import io.github.cmmplb.websocket.domain.vo.ContactsVO;

public interface ContactsService extends IService<Contacts> {

    PageResult<ContactsVO> getByPaged(QueryPageBean bean);

    ContactsInfoVO getInfoById(Long id);
}
