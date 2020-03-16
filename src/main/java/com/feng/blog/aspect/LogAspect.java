package com.feng.blog.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by Feng on 2020/3/4.
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.feng.blog.web.*.*(..))")
    public void Log(){}

    @Before("Log()")
    public void doBefore(JoinPoint point){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String ip=request.getRemoteAddr();
        String url=request.getRequestURI();
        String classMethod=point.getSignature().getDeclaringTypeName()+"."+point.getSignature().getName();
        Object[] args = point.getArgs();
        RequestLog requestLog=new RequestLog(ip, url, classMethod, args);
        logger.info("Request : {}",requestLog);
    }
    @After("Log()")
    public void doAfter(){
        /*logger.info("--------------doAfter----------------");*/
    }
    @AfterReturning(returning="result",pointcut = "Log()")
    public void doAfterRuturn(Object result){
        logger.info("Result : {}",result);
    }

    private class RequestLog{
       private String ip;
       private String url;
       private String classMethod;
       private Object[] args;

        public RequestLog(String ip, String url, String classMethod, Object[] args) {
            this.ip = ip;
            this.url = url;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "id='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
