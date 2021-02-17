package com.banking.cache.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.banking.dto.CustomerDetailsDto;

@Repository
@SuppressWarnings("all")
public class CacheRepository {

    private ValueOperations valueoperations;
    private RedisTemplate redisTemplate;

    public CacheRepository(RedisTemplate redisTemplate) {
        this.valueoperations=redisTemplate.opsForValue();
        this.redisTemplate = redisTemplate;
    }


    public void save(String key,List<Object> map,int ttl) {
    	this.valueoperations.set(key, map);
        redisTemplate.expire(key,ttl,TimeUnit.HOURS);
    }


    public List<Object> findAll(String key){
        System.out.println();
        return (List<Object>) valueoperations.get(key);
    }

}
