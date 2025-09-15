package io.github.cmmplb.i18n.handler;


import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.SpringUtil;
import io.github.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * @author plb
 * @date 2020/6/12 9:58
 * 全局异常捕获
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> implements ResponseBodyAdvice<T> {

    @Override
    public T beforeBodyWrite(T body, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
        Environment env = SpringUtil.getBean(Environment.class);
        Boolean property = env.getProperty("context.enabled", Boolean.class);
        // 开启打印信息
        if (Boolean.TRUE.equals(property)) {
            if (body instanceof Result) {
                Result<?> result = (Result<?>) body;
            }
            log.info("mediaType:{}, body:{}", mediaType, JSON.toJSONString(body));
        }
        return body;
    }

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class aClass) {
        return true;
    }

    @ExceptionHandler({Exception.class})
    public Result<?> exceptionHandler(Exception e) {

        // 参数异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException m = (MethodArgumentNotValidException) e;
            return buildParamsValidException(m.getBindingResult());
        }

        if ((e instanceof MissingServletRequestParameterException)) {
            MissingServletRequestParameterException m = (MissingServletRequestParameterException) e;
            String message = String.format("请求参数错误,请检查参数:'%s'不能为空", m.getParameterName());
            return ResultUtil.custom(HttpCodeEnum.INVALID_REQUEST.getCode(), message);
        }

        // 字段绑定异常
        if (e instanceof BindException) {
            BindException b = (BindException) e;
            return buildParamsValidException(b.getBindingResult());
        }

        // 处理请求参数格式错误 @RequestParam 上 validate 失败后抛出的异常是 ConstraintViolationException
        if ((e instanceof ConstraintViolationException)) {
            ConstraintViolationException b = (ConstraintViolationException) e;
            // String message = b.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            return ResultUtil.custom(HttpCodeEnum.NO_SUCH_MESSAGE_ERROR);
        }

        // 上述异常都没匹配
        log.error(e.getMessage(), e);
        setStatusCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        return ResultUtil.custom(HttpCodeEnum.INTERNAL_SERVER_ERROR);
    }

    private static Result<Object> buildParamsValidException(BindingResult m) {
        String defaultMessage = null;
        String field = null;
        if (!CollectionUtils.isEmpty(m.getAllErrors())) {
            for (ObjectError allError : m.getAllErrors()) {
                FieldError fieldError = (FieldError) allError;
                field = StringUtil.appendMark(field, StringConstant.COMMA, fieldError.getField());
                if (StringUtil.isNotBlank(fieldError.getDefaultMessage())) {
                    if (!fieldError.getDefaultMessage().contains(fieldError.getField())) {
                        defaultMessage = StringUtil.appendMark(defaultMessage, StringConstant.COMMA, fieldError.getField() + StringConstant.SPACE + fieldError.getDefaultMessage());
                    } else {
                        defaultMessage = StringUtil.appendMark(defaultMessage, StringConstant.COMMA, fieldError.getDefaultMessage());
                    }
                }
            }
            if (!StringUtil.isEmpty(defaultMessage)) {
                return ResultUtil.custom(HttpCodeEnum.INVALID_REQUEST.getCode(), defaultMessage);
            }
        }
        return ResultUtil.custom(HttpCodeEnum.INVALID_REQUEST);
    }

    private void setStatusCode(int code) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(Objects.requireNonNull(requestAttributes).getResponse()).setStatus(code);
    }
}

