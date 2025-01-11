package com.catsriding.octalink.core.domain.factory;

import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.core.domain.Url;

public class UrlFactory {

    public static Url newInstance(ShortenUrlCommand command) {
        return Url.of(
                command.url(),
                command.clientIp(),
                command.requestedAt()
        );
    }
}
