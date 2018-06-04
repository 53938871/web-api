package cn.meilituibian.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ResponseEntityAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void responseAfterMethod(ProceedingJoinPoint joinPoint) {
        System.out.println(">>>>>>>>>>><<<<<<");
    }
}
