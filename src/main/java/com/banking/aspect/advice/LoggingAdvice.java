package com.banking.aspect.advice;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

	
	@Pointcut("@within(com.banking.aspect.customAnnotation.Logging)")
	public void loggingPointCut() {
		
	}
	
	@Before("loggingPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        final Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        log.debug("Entering in Method :  " + joinPoint.getSignature().getName());
        log.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
        log.debug("Target class : " + joinPoint.getTarget().getClass().getName());
    }

    @AfterReturning(pointcut = "loggingPointCut() ", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        final Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String returnValue = this.getValue(result);
		log.debug("Method Return value : " + returnValue);
    }

    @AfterThrowing(pointcut = "loggingPointCut() ", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        final Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        log.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        log.error("Cause : " + exception.getCause());
    }
 
	@Around("loggingPointCut()")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable{
		
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		try {
			Signature signature = joinPoint.getSignature();
			Object[] args = joinPoint.getArgs();
			String argList = Arrays.toString(args);
	 		logger.info("Execution Started for - "  + signature.getDeclaringTypeName() + "." + signature.getName());
			Object proceed = joinPoint.proceed(args);
			logger.info("Execution Ended for - "  + signature.getDeclaringTypeName() + "." + signature.getName());			
			return proceed;
		}
		catch(IllegalArgumentException e) {
			logger.error("Illegal Argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
			throw e;
		}
		
		
		
	}
	
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void allServiceMethodsPointCut() {
		
	}
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void allControllerMethodsPointCut() {
		
	}

	private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}
