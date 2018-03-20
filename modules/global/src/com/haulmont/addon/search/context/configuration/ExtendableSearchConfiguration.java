package com.haulmont.addon.search.context.configuration;

import com.haulmont.addon.search.context.SearchConfiguration;
import com.haulmont.addon.search.strategy.SearchStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides additional logic for dynamic add/remove strategy for search component configuration.
 */
public class ExtendableSearchConfiguration implements SearchConfiguration {
    protected Map<String, SearchStrategy> searchStrategyMap = new HashMap<>();

    public ExtendableSearchConfiguration() {}

    public ExtendableSearchConfiguration(SearchConfiguration basicConfiguration) {
        searchStrategyMap.putAll(basicConfiguration.strategyProviders());
    }

    @Override
    public Map<String, SearchStrategy> strategyProviders() {
        return Collections.unmodifiableMap(searchStrategyMap);
    }

    public void addStrategy(SearchStrategy searchStrategy) {
        searchStrategyMap.put(searchStrategy.name(), searchStrategy);
    }

    public void removeStrategy(String name) {
        searchStrategyMap.remove(name);
    }
}
