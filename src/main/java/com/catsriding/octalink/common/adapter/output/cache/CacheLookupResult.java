package com.catsriding.octalink.common.adapter.output.cache;

import java.util.function.Function;

public record CacheLookupResult<D>(
        D domain,
        boolean isCacheHit
) {

    public static <C, D> CacheLookupResult<D> hit(C cache, Function<C, D> mapper) {
        D domain = mapper.apply(cache);
        return new CacheLookupResult<>(domain, true);
    }

    public static <C, D> CacheLookupResult<D> miss() {
        return new CacheLookupResult<>(null, false);
    }

    public CacheLookupResult<D> resolveCacheMiss(D domain) {
        return new CacheLookupResult<>(domain, false);
    }
}