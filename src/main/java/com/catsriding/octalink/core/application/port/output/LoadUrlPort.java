package com.catsriding.octalink.core.application.port.output;

import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.core.application.model.query.ResolveUrlQuery;
import com.catsriding.octalink.core.domain.Url;

public interface LoadUrlPort {

    boolean isUrlRegistered(String url);

    Url load(ShortenUrlCommand command);

    Url load(ResolveUrlQuery query);
}
