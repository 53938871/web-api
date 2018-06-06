package cn.meilituibian.api.aspect;

import cn.meilituibian.api.common.ErrorCode;
import cn.meilituibian.api.common.ResponseMeta;
import cn.meilituibian.api.exception.ErrorResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.aspectj.lang.annotation.Around;


@Aspect
@Configuration
public class ResponseEntityAspect {
    private static final Logger LOGGER = LogManager.getLogger(ResponseEntityAspect.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) joinPoint.proceed();
            HttpStatus httpStatus = responseEntity.getStatusCode();
            Object responseBody = responseEntity.getBody();
            ResponseMeta responseMeta = new ResponseMeta();
            HttpHeaders responseHeaders = responseEntity.getHeaders();
            if (responseBody instanceof  ErrorResponseEntity) {
                ErrorResponseEntity errorResponseEntity = (ErrorResponseEntity)responseBody;
                responseMeta.setMessage(errorResponseEntity.getErrorMsg());
                responseMeta.setCode(errorResponseEntity.getErrorCode());
                responseMeta.setData(errorResponseEntity.getData());
                return new ResponseEntity<>(responseMeta, responseHeaders, HttpStatus.OK);
            }

            responseMeta.setData(responseBody);
            ErrorCode errorCode = ErrorCode.getErrorCode(httpStatus.value() == 200 ? 0 : httpStatus.value());
            responseMeta.setCode(errorCode.getCode());
            responseMeta.setMessage(errorCode.getMessage());
            return new ResponseEntity<>(responseMeta, responseHeaders, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Response entity aspect 错误.", e);
            throw e;
        }

    }

    @Pointcut("execution(public * cn.meilituibian.api.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("good kdfasf sdfafsdfsd55 1");
    }
}

