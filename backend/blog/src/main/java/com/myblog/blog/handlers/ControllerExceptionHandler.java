package com.myblog.blog.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 错误处理
@ControllerAdvice
public class ControllerExceptionHandler {
    // 设置Logger
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 返回一个页面
    // 异常处理 只要是Exception类的都可以被处理
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);

        // 如果Annotation的responseStatus不存在，就抛出这个异常，让springboot直接处理 比如404
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        // 返回的页面名字
        mv.setViewName("error/error");
        return mv;
    }
}
