package com.catsriding.octalink.core.adapter.input.api.request;

import com.catsriding.octalink.core.adapter.input.api.exception.InvalidUrlException;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public record ResolveUrlRequest(
        String shortUrl) {

    private static final String SHORT_URL_REGEX = "^[a-zA-Z0-9]*$";
    private static final int MAX_SHORT_URL_LENGTH = 8;

    public static ResolveUrlRequest bind(String shortUrl) {
        return new ResolveUrlRequest(shortUrl);
    }

    public ResolveUrlQuery toQuery(String clientIp) {
        validate(clientIp);
        return new ResolveUrlQuery(shortUrl, clientIp, LocalDateTime.now());
    }

    private void validate(String clientIp) {
        if (!StringUtils.hasText(shortUrl)) {
            log.info("validate: The short url is empty - clientIp={}", clientIp);
            throw new InvalidUrlException("The provided URL is empty. Please provide a valid URL.");
        }
        if (!Pattern.matches(SHORT_URL_REGEX, shortUrl)) {
            log.info("validate: The short url format is invalid - shortUrl={}, clientIp={}",
                    shortUrl,
                    clientIp);
            throw new InvalidUrlException("The provided URL is invalid. Please provide a valid URL.");
        }
        if (shortUrl.length() > MAX_SHORT_URL_LENGTH) {
            log.info("validate: The url exceeds the maximum allowed length - shortUrl={}, clientIp={}",
                    shortUrl,
                    clientIp);
            throw new InvalidUrlException("The provided URL exceeds the maximum allowed length of 2,048 characters");
        }
    }

}
