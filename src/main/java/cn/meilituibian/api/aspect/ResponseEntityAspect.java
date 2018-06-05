package cn.meilituibian.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class ResponseEntityAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) joinPoint.proceed();
            long end = System.currentTimeMillis();
            HttpStatus httpStatus = responseEntity.getStatusCode();



            Object responseBody = responseEntity.getBody();

            Object response = null;

            if (joinPoint.getSignature() instanceof MethodSignature) {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Method method = signature.getMethod();

                Map map = new HashMap<>();
                map.put("data", responseBody);
                response = map;

            }



            HttpHeaders responseHeaders = responseEntity.getHeaders();

            return new ResponseEntity<>(response, responseHeaders, httpStatus);

        } catch (Exception e) {
            return null;
        }

    }

    @Pointcut("execution(public * cn.meilituibian.api.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("good kdfasf sdfafsdfsd55 1");
    }
}

