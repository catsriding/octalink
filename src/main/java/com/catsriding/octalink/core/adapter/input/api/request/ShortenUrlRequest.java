package com.catsriding.octalink.core.adapter.input.api.request;

import com.catsriding.octalink.core.adapter.input.api.exception.InvalidUrlException;
import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public record ShortenUrlRequest(String url) {

    private static final String URL_REGEX = "^(https?://)[a-zA-Z0-9._~:/?#\\\\@!$&'()*+,;=%-]*$";
    private static final int MAX_URL_LENGTH = 2048;

    public ShortenUrlCommand toCommand(String clientIp) {
        validate(clientIp);
        return new ShortenUrlCommand(url, clientIp, LocalDateTime.now());
    }

    private void validate(String clientIp) {
        if (!StringUtils.hasText(url)) {
            log.info("validate: The url is empty - clientIp={}", clientIp);
            throw new InvalidUrlException("The provided URL is empty. Please provide a valid URL.");
        }
        if (!Pattern.matches(URL_REGEX, url)) {
            log.info("validate: The url format is invalid - url={}, clientIp={}",
                    url,
                    clientIp);
            throw new InvalidUrlException("The provided URL is invalid. Please provide a valid URL.");
        }
        if (url.length() > MAX_URL_LENGTH) {
            log.info("validate: The url exceeds the maximum allowed length of 2,048 characters - url={}, clientIp={}",
                    url,
                    clientIp);
            throw new InvalidUrlException("The provided URL exceeds the maximum allowed length of 2,048 characters");
        }
    }
}
