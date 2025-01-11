package com.catsriding.octalink.adapter.output.persistence.repository;

import com.catsriding.octalink.adapter.output.persistence.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByLongUrl(String string);

    boolean existsByShortUrl(@NotNull String shortUrl);

    boolean existsByLongUrl(@NotNull String longUrl);
}