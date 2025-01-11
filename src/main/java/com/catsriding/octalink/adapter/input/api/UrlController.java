package com.catsriding.octalink.adapter.input.api;

import com.catsriding.octalink.adapter.input.api.request.ShortenUrlRequest;
import com.catsriding.octalink.adapter.input.api.response.UrlResponse;
import com.catsriding.octalink.application.port.input.ShortenUrlUseCase;
import com.catsriding.octalink.common.api.ApiResponse;
import com.catsriding.octalink.common.utils.ClientIpResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final ClientIpResolver clientIpResolver;
    private final ShortenUrlUseCase shortenUrlUseCase;

    @PostMapping("/shorten")
    public ResponseEntity<?> urlShortenerApi(
            HttpServletRequest servletRequest,
            @RequestBody ShortenUrlRequest request) {
        String clientIp = clientIpResolver.resolve(servletRequest);
        UrlResponse response = shortenUrlUseCase.shorten(request.toCommand(clientIp));
        return ResponseEntity.ok(ApiResponse.success(response, "Successfully generated a short url."));
    }

}
