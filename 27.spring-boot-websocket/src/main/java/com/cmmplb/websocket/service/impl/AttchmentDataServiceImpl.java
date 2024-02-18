package com.cmmplb.websocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmmplb.core.exception.BusinessException;
import com.cmmplb.websocket.dao.AttachmentDataMapper;
import com.cmmplb.websocket.dao.AttachmentMapper;
import com.cmmplb.websocket.entity.Attachment;
import com.cmmplb.websocket.entity.AttachmentData;
import com.cmmplb.websocket.service.AttachmentDataService;
import com.cmmplb.websocket.service.AttachmentService;
import com.cmmplb.websocket.vo.AttachmentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author penglibo
 * @date 2023-12-19 13:56:02
 * @since jdk 1.8
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AttchmentDataServiceImpl extends ServiceImpl<AttachmentDataMapper, AttachmentData> implements AttachmentDataService {

}
