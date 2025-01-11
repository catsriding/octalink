package com.catsriding.octalink.core.adapter.input.api;

import static java.util.concurrent.TimeUnit.DAYS;
import static org.springframework.http.CacheControl.maxAge;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import com.catsriding.octalink.common.adapter.input.api.ApiResponse;
import com.catsriding.octalink.common.adapter.input.api.ClientIpResolver;
import com.catsriding.octalink.core.adapter.input.api.request.ResolveUrlRequest;
import com.catsriding.octalink.core.adapter.input.api.request.ShortenUrlRequest;
import com.catsriding.octalink.core.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.core.application.port.input.ResolveUrlUseCase;
import com.catsriding.octalink.core.application.port.input.ShortenUrlUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final ClientIpResolver clientIpResolver;
    private final ShortenUrlUseCase shortenUrlUseCase;
    private final ResolveUrlUseCase resolveUrlUseCase;

    @PostMapping("/shorten")
    public ResponseEntity<?> urlShortenerApi(
            HttpServletRequest servletRequest,
            @RequestBody ShortenUrlRequest request) {
        String clientIp = clientIpResolver.resolve(servletRequest);
        UrlResponse response = shortenUrlUseCase.shorten(request.toCommand(clientIp));
        return ResponseEntity
                .ok(ApiResponse.success(response, "Successfully generated a short url."));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> urlResolverApi(
            HttpServletRequest servletRequest,
            @PathVariable String shortUrl) {
        String clientIp = clientIpResolver.resolve(servletRequest);
        UrlResponse response = resolveUrlUseCase.resolve(ResolveUrlRequest.bind(shortUrl).toQuery(clientIp));
        return ResponseEntity
                .status(MOVED_PERMANENTLY)
                .header(LOCATION, response.originalUrl())
                .header(CACHE_CONTROL, maxAge(1, DAYS).getHeaderValue())
                .body(ApiResponse.success(response, "Successfully resolved a url."));
    }

}
