package io.github.cmmplb.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.websocket.domain.dto.MessageRecordPageQueryDTO;
import io.github.cmmplb.websocket.domain.entity.MessageRecord;
import io.github.cmmplb.websocket.domain.vo.MessageRecordVO;

public interface MessageRecordService extends IService<MessageRecord> {


    PageResult<MessageRecordVO> getByPaged(MessageRecordPageQueryDTO dto);

    boolean read(String uuid);

    Boolean readBusiness(String businessId, Long userId);
}
