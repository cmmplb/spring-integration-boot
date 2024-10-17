package io.github.cmmplb.websocket.convert;

import io.github.cmmplb.core.converter.Converter;
import io.github.cmmplb.websocket.domain.entity.Attachment;
import io.github.cmmplb.websocket.domain.vo.AttachmentVO;
import org.mapstruct.Mapper;

/**
 * @author penglibo
 * @date 2022-08-03 16:56:25
 * @since jdk 1.8
 */

@Mapper
public interface AttachmentConvert extends Converter<Attachment, AttachmentVO> {

}
