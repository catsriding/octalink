package com.catsriding.octalink.core.adapter.output.persistence.entity;

import com.catsriding.octalink.core.domain.Url;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "urls")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "short_url", nullable = false, length = 8)
    private String shortUrl;

    @NotNull
    @Column(name = "long_url", nullable = false, length = 2048)
    private String longUrl;

    @NotNull
    @ColumnDefault("'127.0.0.1'")
    @Column(name = "client_ip", nullable = false, length = 45)
    private String clientIp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static UrlEntity from(Url url) {
        UrlEntity entity = new UrlEntity();
        entity.shortUrl = url.getShortUrl();
        entity.longUrl = url.getLongUrl();
        entity.clientIp = url.getClientIp();
        entity.createdAt = url.getCreatedAt();
        return entity;
    }

    public Url toDomain() {
        return Url.builder()
                .id(id)
                .shortUrl(shortUrl)
                .longUrl(longUrl)
                .clientIp(clientIp)
                .createdAt(createdAt)
                .build();
    }
}