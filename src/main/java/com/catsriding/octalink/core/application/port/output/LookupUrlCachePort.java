package com.catsriding.octalink.core.application.port.output;

import com.catsriding.octalink.common.adapter.output.cache.CacheLookupResult;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import com.catsriding.octalink.core.domain.Url;

public interface LookupUrlCachePort {

    CacheLookupResult<Url> lookup(ResolveUrlQuery query);

}
