package com.catsriding.octalink.core.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class Url {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final int SHORT_URL_MIN_LENGTH = 6;

    private final Long id;
    private final String shortUrl;
    private final String longUrl;
    private final String hashedUrl;
    private final String clientIp;
    private final LocalDateTime createdAt;

    @Builder
    public Url(Long id, String shortUrl, String longUrl, String hashedUrl, String clientIp, LocalDateTime createdAt) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.hashedUrl = hashedUrl;
        this.clientIp = clientIp;
        this.createdAt = createdAt;
    }

    public static Url of(String originalUrl, String clientIp, LocalDateTime requestedAt) {
        String hashedUrl = hashing(originalUrl);
        return Url.builder()
                .shortUrl(hashedUrl.substring(0, SHORT_URL_MIN_LENGTH))
                .longUrl(originalUrl)
                .hashedUrl(hashedUrl)
                .clientIp(clientIp)
                .createdAt(requestedAt)
                .build();
    }

    public Url adjustShortUrlLength() {
        int nextShortUrlLength = calculateNextShortUrlLength();
        return Url.builder()
                .shortUrl(hashedUrl.substring(0, nextShortUrlLength))
                .longUrl(longUrl)
                .hashedUrl(hashedUrl)
                .clientIp(clientIp)
                .createdAt(createdAt)
                .build();
    }

    public int countAdjustedShortUrlLength() {
        return shortUrl.length() - SHORT_URL_MIN_LENGTH + 1;
    }

    private int calculateNextShortUrlLength() {
        return shortUrl.length() + 1;
    }

    private static String hashing(String originalUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("hashing: SHA-1 algorithm not found");
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }

}
