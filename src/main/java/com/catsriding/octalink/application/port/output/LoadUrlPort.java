package com.catsriding.octalink.application.port.output;

import com.catsriding.octalink.application.model.command.ShortenUrlCommand;
import com.catsriding.octalink.domain.Url;

public interface LoadUrlPort {

    boolean isUrlRegistered(String url);

    Url load(ShortenUrlCommand command);
}
