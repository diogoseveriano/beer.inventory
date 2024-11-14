package service;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CaffeineCache;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CacheService {

    @CacheName("findAllUnits")
    Cache cache;

    public Set<String> getAllCacheKeys() {
        return cache.as(CaffeineCache.class).keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

}
