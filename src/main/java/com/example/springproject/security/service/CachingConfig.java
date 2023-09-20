package com.example.springproject.security.service;

import org.cache2k.CacheManager;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@Configurable
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SpringCache2kCacheManager cacheManager = new SpringCache2kCacheManager();
        if (cacheManager.getCacheNames().stream()
                .filter(name -> name.equals("orderCaching"))
                .count() == 0) {
            cacheManager.addCaches(
                    b -> b.name("orderCaching")
            );
        }
        return cacheManager.getNativeCacheManager();
    }
}
