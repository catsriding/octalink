package com.catsriding.octalink.application.port.input;

import com.catsriding.octalink.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.application.model.command.ShortenUrlCommand;

public interface ShortenUrlUseCase {

    UrlResponse shorten(ShortenUrlCommand command);

}
