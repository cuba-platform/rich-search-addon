package com.haulmont.components.search.provider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(SearchProviderFactory.NAME)
public class DefaultSearchProviderFactory implements SearchProviderFactory {

    private SearchProvider defaultProvider;
    private Map<SearchProviderType, SearchProvider> registryMap = new ConcurrentHashMap<>();

    @Inject
    public DefaultSearchProviderFactory(@Qualifier("menu") SearchProvider mainMenuSearchProvider) {
        defaultProvider = mainMenuSearchProvider;
        registryMap.put(DefaultSearchProvider.MENU, mainMenuSearchProvider);
    }

    @Override
    public SearchProvider defaultProvider() {
        return defaultProvider;
    }

    @Override
    public SearchProvider provider(SearchProviderType type) {
        return type == null ? defaultProvider: registryMap.getOrDefault(type, defaultProvider);
    }
}
