package com.myblog.blog.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 定义切面 -> execution() 中定义拦截哪些东西 com.myblog.blog.web下的所有class中的所有method()
    @Pointcut("execution(* com.myblog.blog.web.*.*(..))")
    public void log() {}

    // 切之前执行 -> 把切面传进去
    @Before("log()")
    // 传递一个joinpoint 对象来获取使用了哪些方法
    public void doBefore(JoinPoint joinPoint) {
        // 获得attributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 在attributes中获得request
        HttpServletRequest request = attributes.getRequest();

        // 在request中获得url, ip
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        // 在joinpoint中获得 类.方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLogObject requestLog = new RequestLogObject(url, ip, classMethod, args);
        logger.info("------ doBefore ------ Request: {}", requestLog);
    }

    // 切之后执行
    @After("log()")
    public void doAfter() {
        logger.info("------- doAfter -------");
    }

    // 一些方法执行完后进行拦截
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result: {}" + result);
    }

    private class RequestLogObject {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;      //请求的参数

        public RequestLogObject(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
