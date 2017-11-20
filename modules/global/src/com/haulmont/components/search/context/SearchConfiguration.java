package com.haulmont.components.search.context;

import com.haulmont.components.search.provider.SearchProviderType;

import java.util.List;

public interface SearchConfiguration {
    List<SearchProviderType> providers();
}
