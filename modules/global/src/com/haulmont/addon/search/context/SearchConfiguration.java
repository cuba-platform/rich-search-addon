package com.haulmont.addon.search.context;

import com.haulmont.addon.search.strategy.SearchStrategy;

import java.util.Map;

/**
 * <p>Contains configuration params for searching.<br />
 * Belongs to presenter as strategy holder for search component.
 * </p>
 * Basic mutable implementation:
 * {@link com.haulmont.addon.search.context.configuration.ExtendableSearchConfiguration}
 */
@FunctionalInterface
public interface SearchConfiguration {

    /**
     * @return <b>immutable</b> strategy collection, mapped by name
     */
    Map<String, SearchStrategy> strategyProviders();
}
