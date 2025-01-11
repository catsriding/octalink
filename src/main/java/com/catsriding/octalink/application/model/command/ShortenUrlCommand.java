package com.catsriding.octalink.application.model.command;

import java.time.LocalDateTime;

public record ShortenUrlCommand(
        String url,
        String clientIp,
        LocalDateTime requestedAt
) {

}
