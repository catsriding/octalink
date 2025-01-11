package com.catsriding.octalink.application.port.output;

import com.catsriding.octalink.domain.Url;

public interface PersistUrlPort {

    Url persist(Url url);

}
