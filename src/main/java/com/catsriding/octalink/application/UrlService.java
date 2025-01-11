package com.catsriding.octalink.application;

import com.catsriding.octalink.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.application.port.input.ShortenUrlUseCase;
import com.catsriding.octalink.application.port.output.CacheUrlPort;
import com.catsriding.octalink.application.port.output.LoadUrlPort;
import com.catsriding.octalink.application.port.output.PersistUrlPort;
import com.catsriding.octalink.domain.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService implements ShortenUrlUseCase {

    private final LoadUrlPort loadUrlPort;
    private final PersistUrlPort persistUrlPort;
    private final CacheUrlPort cacheUrlPort;

    @Override
    public UrlResponse shorten(ShortenUrlCommand command) {
        Url url = registerIfAbsent(command);
        cacheUrlPort.cache(url);

        log.info("shorten: Successfully generated a short url - id={}, shortUrl={}, clientIp={}",
                url.getId(),
                url.getShortUrl(),
                url.getClientIp());

        return new UrlResponse(url.getShortUrl(), url.getLongUrl());
    }

    private Url registerIfAbsent(ShortenUrlCommand command) {
        return !loadUrlPort.isUrlRegistered(command.url())
                ? persistUrlPort.persist(Url.of(command))
                : loadUrlPort.load(command);
    }
}
