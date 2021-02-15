package com.banking.aspect.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {
	
	@Pointcut("@annotation(com.banking.aspect.customAnnotation.TrackExecutionTime)")
	public void executionTimePointCut() {

	}
	
	@Around("executionTimePointCut()")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable{
		
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		try {
			Signature signature = joinPoint.getSignature();
			Object[] args = joinPoint.getArgs();
			long start = System.currentTimeMillis();
			Object proceed = joinPoint.proceed(args);
			long end = System.currentTimeMillis();
			logger.info("Execution for - "  + signature.getDeclaringTypeName() 
				+ "." + signature.getName() + " completed in "
				+ (end-start)*1.0/1000 + " sec.");
			return proceed;
		}
		catch(IllegalArgumentException e) {
			logger.error("Illegal Argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
			throw e;
		}
	}
}
