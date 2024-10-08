package com.cmmplb.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.PageResult;
import com.cmmplb.websocket.domain.dto.MessageRecordPageQueryDTO;
import com.cmmplb.websocket.domain.entity.MessageRecord;
import com.cmmplb.websocket.domain.vo.MessageRecordVO;

public interface MessageRecordService extends IService<MessageRecord> {


    PageResult<MessageRecordVO> getByPaged(MessageRecordPageQueryDTO dto);

    boolean read(String uuid);

    Boolean readBusiness(String businessId, Long userId);
}
