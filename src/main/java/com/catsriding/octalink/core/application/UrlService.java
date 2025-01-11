package com.catsriding.octalink.core.application;

import com.catsriding.octalink.common.adapter.output.cache.CacheLookupResult;
import com.catsriding.octalink.core.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import com.catsriding.octalink.core.application.port.input.ResolveUrlUseCase;
import com.catsriding.octalink.core.application.port.input.ShortenUrlUseCase;
import com.catsriding.octalink.core.application.port.output.LoadUrlPort;
import com.catsriding.octalink.core.application.port.output.LookupUrlCachePort;
import com.catsriding.octalink.core.application.port.output.PersistUrlPort;
import com.catsriding.octalink.core.application.port.output.StoreUrlCachePort;
import com.catsriding.octalink.core.domain.Url;
import com.catsriding.octalink.core.domain.factory.UrlFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService implements ShortenUrlUseCase, ResolveUrlUseCase {

    private final LoadUrlPort loadUrlPort;
    private final PersistUrlPort persistUrlPort;
    private final StoreUrlCachePort storeUrlCachePort;
    private final LookupUrlCachePort lookupUrlCachePort;

    @Override
    public UrlResponse shorten(ShortenUrlCommand command) {
        Url url = registerIfAbsent(command);
        storeUrlCachePort.cache(url);

        log.info("shorten: Successfully generated a short url - id={}, shortUrl={}, clientIp={}",
                url.getId(),
                url.getShortUrl(),
                url.getClientIp());

        return new UrlResponse(url.getShortUrl(), url.getLongUrl());
    }

    private Url registerIfAbsent(ShortenUrlCommand command) {
        return !loadUrlPort.isUrlRegistered(command.url())
                ? persistUrlPort.persist(UrlFactory.newInstance(command))
                : loadUrlPort.load(command);
    }

    @Override
    public UrlResponse resolve(ResolveUrlQuery query) {
        CacheLookupResult<Url> result = lookupUrlCachePort.lookup(query);
        result = handleCacheMiss(query, result);

        log.info("resolve: Successfully resolved a url - id={}, shortUrl={}, clientIp={}",
                result.domain().getId(),
                result.domain().getShortUrl(),
                query.clientIp());

        return new UrlResponse(result.domain().getShortUrl(), result.domain().getLongUrl());
    }

    private CacheLookupResult<Url> handleCacheMiss(ResolveUrlQuery query, CacheLookupResult<Url> result) {
        if (result.isCacheHit()) return result;

        result = result.resolveCacheMiss(loadUrlPort.load(query));
        storeUrlCachePort.cache(result.domain());
        return result;
    }
}
