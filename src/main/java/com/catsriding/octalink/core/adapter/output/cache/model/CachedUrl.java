package com.catsriding.octalink.core.adapter.output.cache.model;

import com.catsriding.octalink.core.domain.Url;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CachedUrl(
        Long id,
        String shortUrl,
        String longUrl,
        LocalDateTime createdAt,
        long ttl) {

    private static final long MILLIS_PER_SECOND = 1_000L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long MINUTES_PER_HOUR = 60L;
    private static final long HOURS_PER_DAY = 24L;

    public static CachedUrl from(Url url, int daysToLive) {
        return new CachedUrl(url.getId(),
                url.getShortUrl(),
                url.getLongUrl(),
                url.getCreatedAt(),
                convertDaysToMillis(daysToLive));
    }

    private static long convertDaysToMillis(int days) {
        return days * HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
    }

    public Url toDomain() {
        return Url.builder()
                .id(id)
                .shortUrl(shortUrl)
                .longUrl(longUrl)
                .createdAt(createdAt)
                .build();
    }
}
