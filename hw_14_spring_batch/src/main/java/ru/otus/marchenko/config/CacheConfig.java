package ru.otus.marchenko.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class CacheConfig {
    @Bean
    public Set<CacheManager> authorsCacheManager(){
        return Set.of(new ConcurrentMapCacheManager("authors"),
                new ConcurrentMapCacheManager("genres"),
                new ConcurrentMapCacheManager("books"));
    }
}
