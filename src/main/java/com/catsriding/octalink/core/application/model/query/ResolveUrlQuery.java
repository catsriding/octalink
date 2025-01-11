package com.catsriding.octalink.core.application.model.query;

import java.time.LocalDateTime;

public record ResolveUrlQuery(
        String shortUrl,
        String clientIp,
        LocalDateTime requestedAt
) {

}
