package com.catsriding.octalink.core.application.model.command;

import java.time.LocalDateTime;

public record ShortenUrlCommand(
        String url,
        String clientIp,
        LocalDateTime requestedAt
) {

}
