package com.haulmont.components.search.context;

import com.haulmont.components.search.strategy.SearchStrategy;

import java.util.Map;

/**
 * Contains configuration params for searching.
 * Belongs to presenter as strategy holder for search component.
 * <br />
 * The basic mutable implementation:
 * @see com.haulmont.components.search.context.configuration.ExtendableSearchConfiguration
 * <br />
 * See also presentation layer:
 * <pre>com.haulmont.components.search.presenter.SearchPresenter</pre>
 */
@FunctionalInterface
public interface SearchConfiguration {

    /**
     * @return <b>immutable</b> search strategy collection, mapped by name
     */
    Map<String, SearchStrategy> strategyProviders();
}
