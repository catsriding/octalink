package com.catsriding.octalink.core.adapter.output.cache;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.catsriding.octalink.common.adapter.output.cache.CacheLookupResult;
import com.catsriding.octalink.core.adapter.output.cache.exception.CacheMissException;
import com.catsriding.octalink.core.adapter.output.cache.model.CachedUrl;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import com.catsriding.octalink.core.application.port.output.LookupUrlCachePort;
import com.catsriding.octalink.core.application.port.output.StoreUrlCachePort;
import com.catsriding.octalink.core.domain.Url;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlRedisAdapter implements StoreUrlCachePort, LookupUrlCachePort {

    private static final String KEY_PREFIX = "octalink:url::";
    private static final int BASE_TTL_DAYS = 7;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void cache(Url url) {
        String key = generateKey(url.getShortUrl());
        CachedUrl cached = CachedUrl.from(url, BASE_TTL_DAYS);
        try {
            String serialized = objectMapper.writeValueAsString(cached);
            redisTemplate.opsForValue().set(key, serialized, cached.ttl(), MILLISECONDS);
            log.info("cache: Successfully cached URL - id={}, shortUrl={}", cached.id(), cached.shortUrl());
        } catch (Exception e) {
            log.error("cache: Failed to cache URL - id={}, shortUrl={}, longUrl={}",
                    cached.id(),
                    cached.shortUrl(),
                    cached.longUrl(),
                    e);
        }
    }

    @Override
    public CacheLookupResult<Url> lookup(ResolveUrlQuery query) {
        String key = generateKey(query.shortUrl());
        try {
            String serialized = redisTemplate.opsForValue().get(key);
            checkCacheHit(serialized);
            CachedUrl cachedUrl = objectMapper.readValue(serialized, CachedUrl.class);
            log.info("lookup: Cache Hit - key={}", key);
            return CacheLookupResult.hit(cachedUrl, CachedUrl::toDomain);
        } catch (CacheMissException e) {
            log.info("lookup: Cache Miss - key={}", key);
            return CacheLookupResult.miss();
        } catch (Exception e) {
            log.error("lookup: Failed to convert cache to domain - key={}", key);
            return CacheLookupResult.miss();
        }
    }

    private static String generateKey(String shortUrl) {
        return KEY_PREFIX + shortUrl;
    }

    private static void checkCacheHit(String serialized) {
        if (!StringUtils.hasText(serialized)) throw new CacheMissException();
    }
}
