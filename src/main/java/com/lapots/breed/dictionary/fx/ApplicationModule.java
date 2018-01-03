package com.lapots.breed.dictionary.fx;

import com.google.inject.AbstractModule;
import com.lapots.breed.dictionary.repository.QueryTemplate;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QueryTemplate.class);
    }
}
