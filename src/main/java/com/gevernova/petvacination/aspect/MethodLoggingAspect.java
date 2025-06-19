package com.gevernova.petvacination.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class MethodLoggingAspect {

    Logger logger = Logger.getLogger(MethodLoggingAspect.class.getName());
    private final String servicePointcut = "execution(* com.gevernova.petvacination.service..*(..))";

    @Before(servicePointcut)
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Attempting to execute " + className + "." + methodName + "()");;

    }

    @AfterReturning(value = servicePointcut,returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Successfully executed " + className + "." + methodName + "(). Returned: " + (result != null ? result.getClass().getSimpleName() : "void/null"));
    }



}
