package com.haulmont.components.search.context;

import com.haulmont.components.search.strategy.SearchStrategy;

import java.util.Map;

@FunctionalInterface
public interface SearchConfiguration {
    Map<String, SearchStrategy> strategyProviders();
}
