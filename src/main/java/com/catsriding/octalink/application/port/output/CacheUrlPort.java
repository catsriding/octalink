package com.catsriding.octalink.application.port.output;

import com.catsriding.octalink.domain.Url;

public interface CacheUrlPort {

    void cache(Url url);

}
