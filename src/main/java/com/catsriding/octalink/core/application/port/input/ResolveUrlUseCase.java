package com.catsriding.octalink.core.application.port.input;

import com.catsriding.octalink.core.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;

public interface ResolveUrlUseCase {

    UrlResponse resolve(ResolveUrlQuery query);

}
