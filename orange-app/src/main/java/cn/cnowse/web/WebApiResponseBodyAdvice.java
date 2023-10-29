package cn.cnowse.web;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cn.cnowse.base.ApiResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "cn.cnowse.controller")
public class WebApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter rt, Class<? extends HttpMessageConverter<?>> converterType) {
        boolean hasRestControllerAnnotation = rt.getDeclaringClass().getAnnotation(RestController.class) != null;
        boolean hasResponseBodyAnnotation = rt.getMethodAnnotation(ResponseBody.class) != null;
        boolean isJsonConverter = MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        boolean isResponseEntity = ResponseEntity.class.isAssignableFrom(rt.getParameterType());
        if (log.isDebugEnabled()) {
            log.info(
                    "hasRestControllerAnnotation={}, hasResponseBodyAnnotation={}, isResponseEntity={}, isJsonConverter={}",
                    hasRestControllerAnnotation, hasResponseBodyAnnotation, isResponseEntity, isJsonConverter);
        }
        return (hasRestControllerAnnotation || hasResponseBodyAnnotation || isResponseEntity) && isJsonConverter;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body instanceof ApiResult) {
            return body;
        }
        Type gpt = returnType.getGenericParameterType();
        if (String.class.equals(gpt)) {
            return body;
        }
        if (Void.TYPE.equals(gpt)) {
            return ApiResult.success();
        }
        return ApiResult.success(body);
    }

}
