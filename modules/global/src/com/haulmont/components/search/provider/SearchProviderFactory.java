package com.haulmont.components.search.provider;

public interface SearchProviderFactory {
    String NAME = "search_SearchProviderFactory";

    SearchProvider defaultProvider();

    SearchProvider provider(SearchProviderType type);
}
