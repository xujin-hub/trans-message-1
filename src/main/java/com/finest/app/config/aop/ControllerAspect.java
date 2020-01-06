package com.finest.app.config.aop;

import javax.servlet.http.HttpServletRequest;

import com.finest.app.config.ParamsConfig;
import com.finest.app.controller.Utils.CountUtils;
import com.finest.app.controller.Utils.PropertiesUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ch.qos.logback.classic.Logger;

/**
 * 切面类
 */
@Aspect
@Component
public class ControllerAspect {

    private Logger logger= (Logger) LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    ParamsConfig paramsConfig;

    /**
     * Pointcut定义切点
     * public修饰符的   返回值任意  com.cy.controller包下面的任意类的任意方法任意参数
     */
    @Pointcut( "execution(* com.finest.app.controller..*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes sra =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String method = request.getParameter("method");
        accessCount(request, method);
        MDC.put("THREAD_ID", method);

    }

    private void accessCount(HttpServletRequest request, String method) {
        if(!paramsConfig.getCount().isEnable())
        {
            return;
        }
        try {
            String servletPath = request.getServletPath();
            if(StringUtils.isEmpty(method))
            {
                if(servletPath.startsWith("/cdupload"))
                {
                    String[] temps = servletPath.split("/");
                    servletPath = "/cdupload/" + temps[2];
                }
            }else
            {
                servletPath = servletPath+"?method-"+method;
            }
            CountUtils.add(servletPath);
        } catch (Exception e)
        {
            logger.info("切面程序异常,不影响业务",e);
        }

    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint){
        MDC.remove("THREAD_ID");
    }

    @AfterReturning(returning="result", pointcut="log()")
    public void doAfterReturnint(Object result){
        logger.info("方法返回值：" + result);
    }
}