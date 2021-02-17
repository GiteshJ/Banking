package com.banking.aspect.advice;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banking.aspect.customAnnotation.Cache;
import com.banking.cache.repository.CacheRepository;
import com.banking.dto.CustomerDetailsDto;

@Aspect
@Component("CacheAdvice")
public class CacheAdvice {
	@Autowired
    CacheRepository cacheRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.banking.aspect.customAnnotation.Cache)")
    public void cacheMethod() { }

    @Around(value = "cacheMethod()")
    public Object Cache(ProceedingJoinPoint joinPoint) throws Throwable{
        Method method = getCurrentMethod(joinPoint);
        String key = method.getName();
        logger.info("Cache ----"+key);
        Object result = cacheRepository.findAll(key);
        List<?> resultMap = (List<?>)result;
        if(result!=null && resultMap.size()>0)
            return resultMap;

        result = joinPoint.proceed();
        Cache cache = method.getAnnotation(Cache.class);
        logger.info("Caching Start----"+key);
        cacheRepository.save(key,(List<Object>) result,cache.ttl());
        logger.info("Caching Done");
        return result;
    }

    private java.lang.reflect.Method getCurrentMethod(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)  joinPoint.getSignature();
        return signature.getMethod();
    }
}
