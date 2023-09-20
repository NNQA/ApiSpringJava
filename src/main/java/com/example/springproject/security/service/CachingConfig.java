package com.example.springproject.security.service;

import org.cache2k.CacheManager;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@Configurable
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SpringCache2kCacheManager cacheManager = new SpringCache2kCacheManager();
        if (cacheManager.getCacheNames().stream().noneMatch(name -> name.equals("orderCaching"))) {
            cacheManager.addCaches(
                    b -> b.name("orderCaching").expireAfterWrite(30, TimeUnit.DAYS)
            );
        }
        return cacheManager.getNativeCacheManager();
    }
}
