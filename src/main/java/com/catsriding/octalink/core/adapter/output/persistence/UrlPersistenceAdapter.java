package com.catsriding.octalink.core.adapter.output.persistence;

import com.catsriding.octalink.core.adapter.output.persistence.entity.UrlEntity;
import com.catsriding.octalink.core.adapter.output.persistence.exception.UrlEntityNotFoundException;
import com.catsriding.octalink.core.adapter.output.persistence.repository.UrlRepository;
import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import com.catsriding.octalink.core.application.port.output.LoadUrlPort;
import com.catsriding.octalink.core.application.port.output.PersistUrlPort;
import com.catsriding.octalink.core.domain.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlPersistenceAdapter implements LoadUrlPort, PersistUrlPort {

    private final UrlRepository urlRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isUrlRegistered(String url) {
        return urlRepository.existsByLongUrl(url);
    }

    @Override
    @Transactional(readOnly = true)
    public Url load(ShortenUrlCommand command) {
        return urlRepository.findByLongUrl(command.url())
                .orElseThrow(() -> {
                    log.error("load: Failed load url entity - longUrl={}", command.url());
                    return new UrlEntityNotFoundException("Cannot find url mapping data");
                })
                .toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Url load(ResolveUrlQuery query) {
        return urlRepository.findByShortUrl(query.shortUrl())
                .orElseThrow(() -> {
                    log.error("load: Failed load url entity - shortUrl={}", query.shortUrl());
                    return new UrlEntityNotFoundException("Cannot find url mapping data");
                })
                .toDomain();
    }

    @Override
    @Transactional
    public Url persist(Url url) {
        while (urlRepository.existsByShortUrl(url.getShortUrl())) {
            log.warn("persist: Already exists short url - count={}, shortUrl={}",
                    url.countAdjustedShortUrlLength(),
                    url.getShortUrl());

            url = url.adjustShortUrlLength();
        }
        url = urlRepository.save(UrlEntity.from(url)).toDomain();

        log.info("persist: Successfully persisted url - id={}, shortUrl={}, clientIp={}",
                url.getId(),
                url.getShortUrl(),
                url.getClientIp());

        return url;
    }
}
