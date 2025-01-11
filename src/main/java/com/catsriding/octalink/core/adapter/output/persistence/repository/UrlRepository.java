package com.catsriding.octalink.core.adapter.output.persistence.repository;

import com.catsriding.octalink.core.adapter.output.persistence.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByLongUrl(@NotNull String string);

    Optional<UrlEntity> findByShortUrl(@NotNull String shortUrl);

    boolean existsByShortUrl(@NotNull String shortUrl);

    boolean existsByLongUrl(@NotNull String longUrl);
}