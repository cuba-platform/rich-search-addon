package com.haulmont.components.search.context.configuration;

import com.haulmont.components.search.context.SearchConfiguration;
import com.haulmont.components.search.strategy.SearchStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The extendable configuration implementation that presents additional logic
 * for dynamic add/remove any strategy for search component configuration.
 * <br />
 * @see SearchConfiguration
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
