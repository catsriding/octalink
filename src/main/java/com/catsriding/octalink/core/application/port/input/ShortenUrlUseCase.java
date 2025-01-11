package com.catsriding.octalink.core.application.port.input;

import com.catsriding.octalink.core.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.core.application.model.command.ShortenUrlCommand;

public interface ShortenUrlUseCase {

    UrlResponse shorten(ShortenUrlCommand command);

}
