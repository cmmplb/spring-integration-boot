package io.github.cmmplb.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.websocket.domain.dto.RecentlyMessagePageQueryDTO;
import io.github.cmmplb.websocket.domain.entity.RecentlyMessage;
import io.github.cmmplb.websocket.domain.vo.RecentlyMessageVO;

/**
* @author penglibo
* @date 2024-01-05 16:55:53
* @since jdk 1.8
*/
public interface RecentlyMessageService extends IService<RecentlyMessage> {

    PageResult<RecentlyMessageVO> getByPaged(RecentlyMessagePageQueryDTO dto);

}
