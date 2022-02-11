package com.example.myusermgmt.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

  public static final String USERS_CACHE = "users";

  @Bean
  public CaffeineCache usersCache() {
    return new CaffeineCache(
        USERS_CACHE,
        Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build()
    );
  }
}
