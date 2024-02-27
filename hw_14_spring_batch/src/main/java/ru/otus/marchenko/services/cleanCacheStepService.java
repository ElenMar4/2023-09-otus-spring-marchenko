package ru.otus.marchenko.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class cleanCacheStepService {

    @CacheEvict(cacheNames = {"authors", "genres", "books"})
    public void cleanCache(){
        log.info("Cleaning cache ...");
    }
}
