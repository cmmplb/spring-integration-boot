package io.github.cmmplb.webservice.client.service;

import io.github.cmmplb.webservice.client.domain.dto.InversionOrderBusinessDTO;
import io.github.cmmplb.webservice.client.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.client.domain.dto.VerificationCodeDTO;
import io.github.cmmplb.webservice.client.domain.vo.InversionOrderBusinessVO;
import io.github.cmmplb.webservice.client.domain.vo.MessageVO;
import io.github.cmmplb.webservice.client.domain.vo.VerificationCodeVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author penglibo
 * @date 2025-03-28 15:17:43
 * @since jdk 1.8
 */

@WebService(targetNamespace = "http://service.webservice.ideal.sh.cn")
public interface HaobaiService {

    @WebMethod
    @WebResult(name = "resultInfo")
    VerificationCodeVO verificationCode(@WebParam(name = "paramdata") VerificationCodeDTO dto);

    @WebMethod
    @WebResult(name = "resultInfo")
    InversionOrderBusinessVO inversionOrderBusiness(@WebParam(name = "paramdata") InversionOrderBusinessDTO dto);
}
