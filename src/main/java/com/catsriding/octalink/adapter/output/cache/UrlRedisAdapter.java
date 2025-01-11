package com.catsriding.octalink.adapter.output.cache;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.catsriding.octalink.adapter.output.cache.model.CachedUrl;
import com.catsriding.octalink.application.port.output.CacheUrlPort;
import com.catsriding.octalink.domain.Url;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlRedisAdapter implements CacheUrlPort {

    private static final String KEY_PREFIX = "octalink:url::";
    private static final int BASE_TTL_DAYS = 7;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void cache(Url url) {
        CachedUrl cached = CachedUrl.from(url, BASE_TTL_DAYS);
        try {
            String serialized = objectMapper.writeValueAsString(cached);
            redisTemplate.opsForValue().set(KEY_PREFIX + cached.shortUrl(), serialized, cached.ttl(), MILLISECONDS);
            log.info("cache: Successfully cached URL - id={}, shortUrl={}", cached.id(), cached.shortUrl());
        } catch (Exception e) {
            log.error("cache: Failed to cache URL - id={}, shortUrl={}, longUrl={}",
                    cached.id(),
                    cached.shortUrl(),
                    cached.longUrl(),
                    e);
        }
    }
}
