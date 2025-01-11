package com.catsriding.octalink.core.application.port.output;

import com.catsriding.octalink.core.domain.Url;

public interface PersistUrlPort {

    Url persist(Url url);

}
